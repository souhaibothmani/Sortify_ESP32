#include <WiFi.h>
#include <HTTPClient.h>
#include "WifiCam.hpp"
#include <ESP32Servo.h> // Include the ESP32Servo library
#include <ArduinoJson.h>  // Include the ArduinoJson library

// Wi-Fi credentials
static const char* WIFI_SSID = "KdG-iDev";
static const char* WIFI_PASS = "KggXDHhEgGHTriQc";

// Global MAC address variable
String macAddress;
bool firstImageSkipped = false; // Flag to track if the first image is skipped

// Ultrasonic sensor pins
const int trigPin = 12; // Trigger pin for ultrasonic sensor
const int echoPin = 13; // Echo pin for ultrasonic sensor

// Ultrasonic sensor variables
long duration;
int distance;

// Servo object and pin
Servo myServo;          // Create a Servo object
const int servoPin = 15; // Servo signal pin

// Brushless motor ESC setup
Servo ESC;  // Create a Servo object to control the ESC
int pwmValues[] = {1200, 1500, 1800}; // 1200 for paper/cardboard, 1500 for plastic, 1800 for glass
int stepDuration = 5000;  // Duration for each step in milliseconds (5 seconds)

void setup() {
    Serial.begin(115200);
    Serial.println();

    // Connect to Wi-Fi
    WiFi.mode(WIFI_STA);
    WiFi.begin(WIFI_SSID, WIFI_PASS);
    if (WiFi.waitForConnectResult() != WL_CONNECTED) {
        Serial.printf("WiFi failure %d\n", WiFi.status());
        delay(5000);
        ESP.restart();
    }
    Serial.println("WiFi connected");
    delay(1000);

    // Get the MAC address of the ESP32
    macAddress = WiFi.macAddress();
    Serial.printf("Device MAC Address: %s\n", macAddress.c_str());

    // Initialize ultrasonic sensor pins
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);

    // Initialize servo
    myServo.attach(servoPin);   // Attach the servo signal pin to the specified pin
    myServo.write(180);         // Set initial position to 180 degrees
    delay(1000);

    // Initialize ESC for the brushless motor
    ESC.attach(14, 1000, 2000); // Pin 18 for ESC control
    ESC.writeMicroseconds(1500); // Set neutral position (ESC initialization)
    delay(3000);  // Wait for ESC initialization (3 seconds)

    // Camera initialization
    using namespace esp32cam;
    Config cfg;
    cfg.setPins(pins::AiThinker);
    cfg.setResolution(Resolution::find(640, 480)); // Lower resolution for faster capture
    cfg.setJpeg(50); // Lower JPEG quality for smaller size

    if (!Camera.begin(cfg)) {
        Serial.println("Camera init failed!");
        while (true) delay(1000);
    }
    Serial.println("Camera init success!");

    // Capture the first image (discarded)
    discardFirstImage();
}

void loop() {
    // Read ultrasonic sensor distance
    distance = readUltrasonicSensor();

    // Check if the distance is within the range (0 < distance < 15)
    if (distance > 0 && distance < 10) {
        Serial.printf("Object detected close, distance: %d cm\n", distance); // Print detected distance
        sendImageToServer();
        // Move the servo to 0 degrees
        myServo.write(70);
        delay(2000); // Wait for 2 seconds

        // Return the servo to 180 degrees
        myServo.write(180);

        // Take and send an image if the condition is met
    } else {
        Serial.printf("No object detected close, distance: %d cm\n", distance); // Print distance if no object is close
    }

    delay(1000); // Delay between measurements to avoid flooding the camera with requests
}

int readUltrasonicSensor() {
    // Trigger the sensor to send an ultrasonic pulse
    digitalWrite(trigPin, LOW);  // Make sure the trigger is low
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH); // Send a pulse
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);  // Stop sending pulse

    // Measure the duration of the pulse that bounces back from an object
    duration = pulseIn(echoPin, HIGH);

    // Calculate distance in centimeters (duration is in microseconds)
    int distanceCm = duration * 0.034 / 2; // Speed of sound is 0.034 cm/µs

    return distanceCm;
}

void discardFirstImage() {
    using namespace esp32cam;

    // Capture and discard the first image
    auto fb = Camera.capture();
    if (!fb) {
        Serial.println("Camera capture failed during first discard!");
        return;
    }
    Serial.println("First image captured and discarded.");
    // No need to send or process this image
}

void sendImageToServer() {
    using namespace esp32cam;

    // Capture an image
    auto fb = Camera.capture();
    if (!fb) {
        Serial.println("Camera capture failed!");
        return;
    }
    Serial.printf("Image captured, size: %zu bytes\n", fb->size());

    // Skip sending the first image
    if (!firstImageSkipped) {
        firstImageSkipped = true; // Set flag to true after skipping
        Serial.println("First image skipped. Ready to send subsequent images.");
        return;
    }

    // Check if WiFi is connected
    if (WiFi.status() == WL_CONNECTED) {
        HTTPClient http;

        // Configure server and headers
        http.begin("http://10.134.178.161/image"); // Replace with your server's IP and endpoint
        http.addHeader("Content-Type", "image/jpeg");
        http.addHeader("X-MAC-Address", macAddress.c_str()); // Add the MAC address as a custom header

        http.setTimeout(10000); // 10 seconds timeout

        // Send the image data
        int httpResponseCode = http.POST(fb->data(), fb->size());
        if (httpResponseCode > 0) {
            Serial.printf("Server response: %d\n", httpResponseCode);
            String response = http.getString(); // Get the server's response body

            Serial.printf("Response body: %s\n", response.c_str());

            // Parse the response to get the predicted material
            String material = parseMaterialFromResponse(response);

            // Control the bin based on the predicted material
            controlBinMovement(material);

            // Open the bin (servo action)
            myServo.write(70);  // Move the servo to 70 degrees
            delay(2000); // Wait for 2 seconds

            // Close the bin
            myServo.write(180);  // Move the servo back to 180 degrees
        } else {
            Serial.printf("POST failed, error: %s\n", http.errorToString(httpResponseCode).c_str());
        }

        http.end();
    } else {
        Serial.println("WiFi not connected!");
    }

    // Frame buffer automatically released
}

// Function to parse the material from the server response using ArduinoJson
String parseMaterialFromResponse(String response) {
    // Create a JSON document to hold the response data
    DynamicJsonDocument doc(1024);

    // Parse the JSON response
    DeserializationError error = deserializeJson(doc, response);
    if (error) {
        Serial.printf("Failed to parse JSON: %s\n", error.c_str());
        return "";
    }

    // Extract the "material" field from the JSON response
    String material = doc["material"].as<String>();
    return material;
}

// Function to control the brushless motor based on material detected
void controlBinMovement(String material) {
    if (material == "paper" || material == "cardboard") {
        Serial.println("Paper/Cardboard detected: Moving to sector 1.");
        ESC.writeMicroseconds(pwmValues[0]); // Set to 1200 for paper/cardboard
    } else if (material == "plastic") {
        Serial.println("Plastic detected: Moving to sector 2.");
        ESC.writeMicroseconds(pwmValues[1]); // Set to 1500 for plastic (neutral)
    } else if (material == "glass") {
        Serial.println("Glass detected: Moving to sector 3.");
        ESC.writeMicroseconds(pwmValues[2]); // Set to 1800 for glass
    } else {
        Serial.println("Unknown material detected.");
    }

    delay(stepDuration); // Hold position for the specified duration before returning to neutral
    ESC.writeMicroseconds(1500); // Return to neutral position (1500 microseconds)
}

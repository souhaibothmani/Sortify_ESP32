#include <WiFi.h>
#include <HTTPClient.h>

// Wi-Fi credentials
const char* ssid = "KdG-iDev";  // Replace with your WiFi SSID
const char* password = "nn3YQQL4PK9CnydA";  // Replace with your WiFi password

// Server URL (your Spring Boot endpoint)
const String serverName = "http://10.134.178.161/bin-open";  // Replace with your server IP and endpoint

// Ultrasonic sensor pins
const int trigPin = 5;  // GPIO pin for TRIG
const int echoPin = 18; // GPIO pin for ECHO

// Variables for distance measurement
long duration;
int distance;

void setup() {
  Serial.begin(115200);

  // Setup ultrasonic sensor pins
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi!");
}

void loop() {
  // Measure distance
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  // Check if bin is open (distance < 10 cm)
  if (distance > 0 && distance < 10) {
    Serial.println("Bin is open!");

    // Send HTTP request to the server
    if (WiFi.status() == WL_CONNECTED) {
      HTTPClient http;
      http.begin(serverName);
      http.addHeader("Content-Type", "application/json");

      // Send a basic notification (no payload needed)
      int httpResponseCode = http.POST("{}");

      if (httpResponseCode > 0) {
        String response = http.getString();
        Serial.println("Server response: " + response);
      } else {
        Serial.println("Error sending data: " + String(httpResponseCode));
      }

      http.end();
      delay(5000);  // Adjust the delay as needed
    } else {
      Serial.println("WiFi disconnected!");
      delay(5000);  // Adjust the delay as needed
    }
  }

}


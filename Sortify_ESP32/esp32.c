#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <curl/curl.h>
#include <wiringPi.h>

// Ultrasonic sensor pins (GPIO numbers may differ)
const int trigPin = 5;  // GPIO pin for TRIG
const int echoPin = 18; // GPIO pin for ECHO

// Server URL (your Spring Boot endpoint)
const char* serverName = "http://10.134.178.161/bin-open";

// Variables for distance measurement
long duration;
int distance;

// Function to measure distance using ultrasonic sensor
int measureDistance() {
    digitalWrite(trigPin, LOW);
    usleep(2);  // delay 2 microseconds
    digitalWrite(trigPin, HIGH);
    usleep(10); // delay 10 microseconds
    digitalWrite(trigPin, LOW);

    // Measure pulse duration on echoPin
    duration = pulseIn(echoPin, HIGH);
    return duration * 0.034 / 2;
}

// Function to send HTTP request using libcurl
void sendHTTPRequest() {
    CURL* curl = curl_easy_init();
    if (curl) {
        curl_easy_setopt(curl, CURLOPT_URL, serverName);
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, "{}");

        CURLcode res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
        }
        curl_easy_cleanup(curl);
    }
}

int main() {
    // Initialize wiringPi library (specific to Raspberry Pi)
    wiringPiSetupGpio();

    // Setup ultrasonic sensor pins
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);

    while (1) {
        // Measure distance
        distance = measureDistance();

        // Check if bin is open (distance < 10 cm)
        if (distance > 0 && distance < 10) {
            printf("Bin is open!\n");
            sendHTTPRequest();
            sleep(5); // Delay 5 seconds
        }

        sleep(1);  // Adjust main loop delay as needed
    }

    return 0;
}

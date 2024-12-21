# Integration_3_Team_11_Sortify_esp32

This project uses an ESP32-CAM to detect objects using an ultrasonic sensor, capture images, and communicate with a server to process the images and classify materials. The system uses a servo motor to open and close a bin based on the material detected. Additionally, a brushless motor (ESC) is used to control the sorting mechanism based on the material type.
## Features

Ultrasonic Sensor: Detects objects within a defined distance range and triggers actions based on proximity.
Camera: Captures images for material classification.
Wi-Fi Connectivity: Connects to a Wi-Fi network to communicate with a server.
Servo Motor: Controls the opening and closing of the bin based on sensor input.
Brushless Motor (ESC): Moves the bin to different sectors based on the material detected (paper/cardboard, plastic, glass).
Material Classification: Sends images to the server, receives a material classification response, and controls the system based on the result.
## Requirements

#### Hardware:
ESP32-CAM module
Ultrasonic sensor (HC-SR04)
Servo motor
Brushless motor with ESC (Electronic Speed Controller)
#### Software:
Arduino IDE with ESP32 support
Libraries:
WiFi.h
HTTPClient.h
ESP32Servo.h
ArduinoJson.h
## Setup

#### Wi-Fi Configuration
In the code, replace the WIFI_SSID and WIFI_PASS values with your Wi-Fi credentials:
static const char* WIFI_SSID = "YOUR_SSID";
static const char* WIFI_PASS = "YOUR_PASSWORD";
#### Camera Configuration
Ensure that the camera is connected correctly and configured with the following:
cfg.setPins(pins::AiThinker); // Set camera pins for AiThinker module
cfg.setResolution(Resolution::find(640, 480)); // Resolution for faster capture
#### Server Configuration
Replace the server IP and endpoint in the sendImageToServer() function with the actual IP address and endpoint where the server is running:
http.begin("http://YOUR_SERVER_IP/image");
#### Pin Assignments
Ultrasonic Sensor: trigPin is connected to pin 12, echoPin to pin 13.
Servo Motor: servoPin is connected to pin 15.
Brushless Motor (ESC): Controlled via pin 14.
#### Camera Initialization
The ESP32-CAM is initialized in the setup() function using the AiThinker module. You can modify the camera configuration as needed.
## Usage

Power the ESP32: Ensure the ESP32 is powered and connected to your Wi-Fi network.
Detection and Action: The system will:
Monitor the distance using the ultrasonic sensor.
If an object is detected within the range (0-10 cm), the servo will open the bin, and an image will be captured.
The image is sent to the server for material classification.
Based on the response (paper/cardboard, plastic, or glass), the brushless motor will move the bin to the corresponding sector.
Debugging: Use the serial monitor to view system status and any error messages.
## Example Workflow

The ultrasonic sensor detects an object close to the bin (distance < 10 cm).
The camera captures an image, which is sent to the server for material classification.
The server returns the predicted material (e.g., "plastic").
Based on the material, the brushless motor moves the bin to the appropriate sector.
The servo motor opens and closes the bin.
## Known Issues

The first image captured by the camera is discarded and not sent to the server.
Ensure that the server is up and running and can handle POST requests with image data.
## Additional Information
The repository includes a ZIP file containing the necessary libraries for the ESP32.
#### To use these libraries:
Unzip the file.
Place the unzipped libraries in Documents -> Arduino -> libraries.
All coding and development for this project were done using the Arduino IDE.
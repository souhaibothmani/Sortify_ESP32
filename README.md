# Ultrasonic Bin Monitoring System with WiFi Notification

This project uses an ESP32 microcontroller to monitor the status of a bin lid using an ultrasonic sensor. When the lid is open (distance detected is less than 10 cm), the ESP32 sends a notification to a Spring Boot server via an HTTP POST request. This project is ideal for smart bin monitoring systems in IoT applications.

---

## Features
- **Ultrasonic Distance Measurement:** Detects the proximity of objects (e.g., bin lid).
- **WiFi Connectivity:** Connects to a local WiFi network to communicate with a server.
- **HTTP Communication:** Sends HTTP POST requests to a specified server endpoint when the bin lid is detected as open.
- **ESP32 Compatible:** Designed for use with ESP32 microcontroller boards.

---

## Components Required
1. **ESP32 Development Board**
2. **Ultrasonic Sensor** (e.g., HC-SR04)
3. **Jumper Wires**
4. **Breadboard** (optional)
5. **WiFi Network** (SSID and Password)

---

## Pin Configuration
- **Trigger Pin (TRIG):** Connect to GPIO 5 on ESP32.
- **Echo Pin (ECHO):** Connect to GPIO 18 on ESP32.

---

## Software Requirements
1. **Arduino IDE** (with ESP32 Board Manager installed)
2. **WiFi Library:** Pre-installed with ESP32 support in Arduino IDE.
3. **HTTPClient Library:** Pre-installed with ESP32 support in Arduino IDE.

---

## Code Overview
1. **Wi-Fi Configuration:**
   - Replace `ssid` and `password` with your WiFi credentials.
2. **Server Configuration:**
   - Update `serverName` with your Spring Boot server endpoint (e.g., `http://<server-ip>/bin-open`).
3. **Ultrasonic Sensor Measurement:**
   - Measures distance using the HC-SR04 sensor.
4. **Distance Threshold:**
   - Checks if the distance is less than 10 cm to detect an open bin.
5. **HTTP Request:**
   - Sends a POST request with an empty JSON payload (`{}`) when the bin is detected as open.

---

## Setup Instructions
1. **Hardware Setup:**
   - Connect the ultrasonic sensor to the ESP32 as per the pin configuration.
   - Ensure proper power supply to the ESP32 and the sensor.
2. **Code Upload:**
   - Open the provided code in Arduino IDE.
   - Select the correct **Board** (`ESP32 Dev Module`) and **Port**.
   - Upload the code to the ESP32.
3. **Run the System:**
   - Power up the ESP32.
   - Open the Serial Monitor to view logs (set baud rate to `115200`).

---

## How It Works
1. The ESP32 connects to the specified WiFi network.
2. The ultrasonic sensor continuously measures the distance between itself and the bin lid.
3. If the distance is less than 10 cm:
   - A message "Bin is open!" is displayed in the Serial Monitor.
   - An HTTP POST request is sent to the specified server endpoint.
4. The server's response is displayed in the Serial Monitor.

---

## Troubleshooting
- **WiFi Not Connecting:**
  - Check SSID and Password.
  - Ensure WiFi network is within range.
- **HTTP Request Failing:**
  - Verify the server endpoint is correct and reachable.
  - Check if the server is running and properly configured to handle POST requests.
- **Incorrect Distance Measurement:**
  - Ensure proper connections to the ultrasonic sensor.
  - Verify the sensor is oriented correctly.

---

## Example Output (Serial Monitor)
```
Connecting to WiFi...
Connected to WiFi!
Distance: 8 cm
Bin is open!
Server response: {"status":"success"}
```

---

## Additional Notes
- Adjust the distance threshold (`<10 cm`) in the code if necessary.
- Add a retry mechanism for failed HTTP requests for more robust communication.
- The delay after a POST request can be modified to optimize the system's response time. 

Enjoy building your smart bin monitoring system! 🎉
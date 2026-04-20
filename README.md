# Sortify — ESP32 Firmware

Firmware for the embedded half of **Sortify**, an automated waste-sorting bin. An ESP32-CAM watches the lid with an ultrasonic sensor, captures an image when something approaches, ships it over Wi-Fi to a classifier, and actuates the bin mechanics based on the response.

Built with the **Arduino IDE** (ESP32 core). Developed for the Integration 3 course — Team 11.

---

## How it fits together

```
       ┌───────────────┐   image    ┌──────────────────────┐
       │  ESP32-CAM    │──────────▶ │  Flask + ResNet50    │
       │  (this repo)  │◀────────── │  (Sortify_predictive)│
       └───────┬───────┘  "plastic" └──────────────────────┘
               │
               ▼
   Ultrasonic  Servo (lid)   Brushless ESC (sector)
```

- The ultrasonic sensor triggers on objects inside `0–10 cm`.
- On trigger, the bin lid opens, the camera captures a frame, and the image is POSTed to the classifier.
- The classifier replies with a material label (`paper/cardboard`, `plastic`, or `glass`).
- The brushless ESC rotates the inner sector to the matching compartment; the servo then closes the lid.

---

## Hardware

| Component | Pin / Note |
|-----------|------------|
| ESP32-CAM (AiThinker module) | camera pins set via `cfg.setPins(pins::AiThinker)` |
| Ultrasonic HC-SR04 | `trigPin = 12`, `echoPin = 13` |
| Servo motor (lid) | `servoPin = 15` |
| Brushless motor + ESC (sector) | pin `14` |

Camera resolution is set to **640×480** for a tight capture-to-response loop.

---

## Files

| File | Role |
|------|------|
| `WifiCam.ino` | Arduino entry point — `setup()` (Wi-Fi, camera, servo/ESC, ultrasonic) and `loop()` (sense → capture → classify → actuate). |
| `WifiCam.hpp` | Shared declarations for the Wi-Fi / camera code. |
| `handlers.cpp` | HTTP handlers / request helpers used by the main sketch. |
| `esp32cam-main.zip` | Bundled library sources — unzip into `Documents/Arduino/libraries/`. |

---

## Setup

1. **Install the Arduino IDE** with ESP32 board support.
2. **Install libraries** — unzip `esp32cam-main.zip` into `Documents/Arduino/libraries/`. You also need `WiFi.h`, `HTTPClient.h`, `ESP32Servo.h`, and `ArduinoJson.h` from the Library Manager.
3. **Configure Wi-Fi** in the sketch:
   ```cpp
   static const char* WIFI_SSID = "YOUR_SSID";
   static const char* WIFI_PASS = "YOUR_PASSWORD";
   ```
4. **Configure the classifier endpoint** in `sendImageToServer()`:
   ```cpp
   http.begin("http://YOUR_SERVER_IP/image");
   ```
5. Select the correct board (AiThinker ESP32-CAM), upload, and open the Serial Monitor at 115200 baud for debug logs.

---

## Runtime flow

1. Ultrasonic sensor detects an object `< 10 cm`.
2. Servo opens the lid.
3. Camera captures a frame (the first frame on boot is intentionally discarded to avoid auto-exposure artifacts).
4. Frame is sent as an HTTP POST to the Flask backend.
5. Response (`plastic` / `glass` / `paper/cardboard`) drives the brushless motor to the corresponding sector.
6. Servo closes the lid; system returns to idle.

---

## Known issues

- The first camera capture after boot is discarded — auto-exposure has not settled yet.
- The backend must be online and reachable on the same Wi-Fi network before the ESP32 can classify.

---

## Team

Integration 3 — Team 11 (Sortify).

# Your Portable House – IoT Smart Home System

A senior project implementing a portable smart home system using ESP8266/ESP32 microcontrollers, ESP32-CAM, a PHP/MySQL backend, and an Android mobile application.

## Features
- Remote monitoring via live camera stream (ESP32-CAM)
- Home automation control (lights, outlets) via ESP8266/ESP32
- Door lock control through web and mobile interfaces
- Telegram bot notifications for security alerts
- Android mobile app for real-time control and monitoring

## Project Structure
```
├── ESP/
│   ├── ESP32-CAM_MJPEG2SD/   # Camera firmware (streaming & motion detection)
│   ├── ESP/MAIN_ESP8266/      # Automation firmware (sensors & actuators)
│   └── ESP/NOOBIX/            # PHP backend & MySQL database
├── Control/                   # Web control panel (door lock)
├── SeniorTrial/               # Android mobile application
├── Jad/                       # ESP32 migration firmware
└── Diagrams/                  # Use case diagrams & project schedule
```

## Team
- Jad Hassan Taha
- Sally

## Technologies
- Microcontrollers: ESP8266, ESP32, ESP32-CAM
- Backend: PHP, MySQL
- Mobile: Android (Java)
- Protocols: HTTP, MQTT, Telegram Bot API

# Flam Project (Android + Web Frame Processing)

## ✅ Features Implemented
- Android app captures camera frames.
- JNI + OpenCV native library (`native-lib.cpp`) processes frames:
  - Converts to grayscale
  - Applies Canny edge detection
- Saves both raw and processed frames as images.
- Web app (`index.html` + `app.js`) displays processed frames in browser.

---
---

## ⚙️ Setup Instructions
### Android
1. Install **Android Studio**.
2. Install **NDK + CMake** via SDK Manager.
3. Clone this repo and open in Android Studio.
4. Sync Gradle, build, and run.

### Dependencies
- OpenCV SDK
- Android NDK
- CMake

---

## 🏗 Architecture
- **Kotlin/Java (MainActivity)** → captures camera frames
- **JNI Bridge** → `processFrame()` calls native code
- **C++ (OpenCV)** → applies grayscale + Canny edge detection
- **Web Viewer** → loads saved images and displays them in browser


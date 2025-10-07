#include <jni.h>
#include <opencv2/opencv.hpp>
#include <android/bitmap.h>

using namespace cv;


extern "C"
JNIEXPORT void JNICALL
Java_com_example_flam_MainActivity_processFrame(
        JNIEnv* env,
        jobject /* this */,
        jobject bitmapIn,
        jobject bitmapOut) {

    AndroidBitmapInfo info;
    void* pixelsIn;
    void* pixelsOut;

    if (AndroidBitmap_getInfo(env, bitmapIn, &info) < 0) return;
    if (AndroidBitmap_lockPixels(env, bitmapIn, &pixelsIn) < 0) return;
    if (AndroidBitmap_lockPixels(env, bitmapOut, &pixelsOut) < 0) return;

    Mat src(info.height, info.width, CV_8UC4, pixelsIn);
    Mat dst(info.height, info.width, CV_8UC4, pixelsOut);

    // Convert to grayscale
    cvtColor(src, dst, COLOR_RGBA2GRAY);

    // Apply Canny edge detection
    Canny(dst, dst, 100, 200);

    // Convert back to RGBA for display in Android
    cvtColor(dst, dst, COLOR_GRAY2RGBA);

    AndroidBitmap_unlockPixels(env, bitmapIn);
    AndroidBitmap_unlockPixels(env, bitmapOut);
}

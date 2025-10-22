#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "NotificationLoggerNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_mua_roti_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    LOGI("Native library initialized successfully");
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_com_mua_roti_NativeLib_addNumbers(
        JNIEnv* env,
        jobject /* this */,
        jint a,
        jint b) {
    jint result = a + b;
    LOGI("Adding %d + %d = %d", a, b, result);
    return result;
}

extern "C" JNIEXPORT void JNICALL
Java_com_mua_roti_NativeLib_logMessage(
        JNIEnv* env,
        jobject /* this */,
        jstring message) {
    const char* nativeMessage = env->GetStringUTFChars(message, 0);
    LOGI("Native log: %s", nativeMessage);
    env->ReleaseStringUTFChars(message, nativeMessage);
}
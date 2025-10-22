package com.mua.roti

/**
 * Native library interface for notification-logger-android
 * This class provides access to native C++ functions through JNI
 */
class NativeLib {
    
    companion object {
        // Load the native library when the class is first used
        init {
            System.loadLibrary("notification-logger-native")
        }
        
        // Create a singleton instance
        @JvmStatic
        val instance = NativeLib()
    }
    
    /**
     * Get a hello string from native code
     * @return Hello message from C++
     */
    external fun stringFromJNI(): String
    
    /**
     * Add two numbers using native code
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    external fun addNumbers(a: Int, b: Int): Int
    
    /**
     * Log a message using native logging
     * @param message Message to log
     */
    external fun logMessage(message: String)
}
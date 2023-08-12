package com.mua.roti.worker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mua.roti.service.NotificationListenerService


class ServiceBootWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun startNotificationListenerService(): Result {
        return try {
            Log.d("d--mua-worker", "trying to start")
            try {
                val serviceIntent = Intent(applicationContext, NotificationListenerService::class.java)
                applicationContext.startService(serviceIntent)
            } catch (ignored: Exception) { }
            Log.d("d--mua-worker", "trying to success")
            Result.success()
        } catch (e: Exception) {
            Log.e("d--mua-worker", "trying to start")
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun doWork(): Result {
        return startNotificationListenerService()
    }
}
package com.mua.roti.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.mua.roti.R
import com.mua.roti.data.datastore.BasicDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EntryService : Service() {
    private val basicDataStore: BasicDataStore = BasicDataStore(this)

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        monitorNotificationService()
        return START_STICKY
    }

    private fun monitorNotificationService() {
        Log.d("d--mua-entry-service", "entry")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("d--mua-entry-service", "entry scope")
            /*
            basicDataStore.serviceRunning.asLiveData().observe(this, Observer {
                Log.d("d--mua-entry-service","$it current status - live data")
            })

             */
            /*
            basicDataStore.serviceRunning.collect{
                Log.d("d--mua-entry-service","$it current status - collect")
            }

             */
            basicDataStore.serviceRunning.onEach {
                val x = basicDataStore.serviceRunning.collect()
                Log.d("d--mua-entry-service", "$it current status - on each")
                Log.d("d--mua-entry-service", "$x current status - on each")
            }
            /*
            basicDataStore.serviceRunning.map {
                Log.d("d--mua-entry-service","$it current status - map")
            }

             */
        }
        /*
        CoroutineScope(Dispatchers.IO).launch {
            if (!basicDataStore.serviceRunning.first()) {
                Toast.makeText(this@EntryService, "service not running", Toast.LENGTH_LONG).show()
                startService(
                    Intent(
                        this@EntryService,
                        NotificationListener::class.java
                    )
                )
            }else{
                Toast.makeText(this@EntryService, "choltese", Toast.LENGTH_LONG).show()
            }
        }
         */
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(notificationManager: NotificationManager): String {
        val channelId = "roti-notification"
        val channelName = resources.getString(R.string.app_name)
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

}
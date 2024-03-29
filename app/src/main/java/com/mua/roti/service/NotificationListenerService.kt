package com.mua.roti.service

import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.mua.roti.data.datastore.BasicDataStore
import com.mua.roti.model.builder.NotificationEntryBuilder
import com.mua.roti.repository.NotificationEntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(api = 18)
class NotificationListenerService : NotificationListenerService() {
    private val notificationEntryRepository: NotificationEntryRepository = NotificationEntryRepository.getInstance(application)
    private val basicDataStore: BasicDataStore = BasicDataStore(this)
    override fun onListenerConnected() {
        super.onListenerConnected()
        CoroutineScope(Dispatchers.IO).launch {
            basicDataStore.setServiceRunningToStore(true)
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        CoroutineScope(Dispatchers.IO).launch {
            basicDataStore.setServiceRunningToStore(false)
        }
    }

    @RequiresApi(api = 19)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val bundle = notification.extras
        val title = bundle.getString(NotificationCompat.EXTRA_TITLE)
        val text = bundle.getString(NotificationCompat.EXTRA_TEXT)
        val key = if (Build.VERSION.SDK_INT >= 20) sbn.key else "----"
        val notificationEntry = NotificationEntryBuilder()
            .setKey(key)
            .setTitle(title)
            .setText(text)
            .build()
        notificationEntryRepository.insert(notificationEntry)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {

        super.onNotificationRemoved(sbn)
    }

}
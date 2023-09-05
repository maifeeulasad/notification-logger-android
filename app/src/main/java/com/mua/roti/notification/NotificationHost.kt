package com.mua.roti.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mua.roti.R
import java.text.SimpleDateFormat
import java.util.*

object NotificationHost {

    private const val CHANNEL_ID = "NOTIFICATION_LOGGER_CHANNEL_ID"
    private val CHANNEL_NAME: CharSequence = "NOTIFICATION_LOGGER_CHANNEL_NAME"

    fun showNotification(context: Context, title: String?, message: String?, intent: Intent?) {
        val uuid = Calendar.getInstance().timeInMillis.toInt()
        val icon = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
        val pendingIntent = PendingIntent
            .getActivity(context, uuid, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(icon)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(uuid, notificationBuilder.build())
    }

    fun showDummyNotification(context: Context, intent: Intent?) {
        val timeStamp: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
        showNotification(context, "Test - Notification Logger Android", timeStamp, intent)
    }
}
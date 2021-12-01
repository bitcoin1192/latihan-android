package com.sisalma.movieticketapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class NotifHelper {
    /*val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "1",
            "General Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "We will notify you when long living operation is done"
        mNotificationManager.createNotificationChannel(channel)
    }

    val notifBuild = NotificationCompat.Builder(this, "3")
        .setContentTitle("Image Uploading")
        .setContentText("Uploading in progress")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(this)) {
        notify(0, notifBuild.build())
    }*/
}
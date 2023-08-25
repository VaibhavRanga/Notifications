package com.example.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.example.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: Notification

    companion object {
        const val CHANNEL_ID = "Promotion1"
        const val CHANNEL_NAME_1 = "Promotions"
        const val CHANNEL_NAME_2 = "Messages"
        const val CHANNEL_NAME_3 = "Backup"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createMyNotification()

        binding.button.setOnClickListener {
            notificationManager.notify(1, notification)
        }
    }

    private fun createMyNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME_1, NotificationManager.IMPORTANCE_HIGH))
        }

        val bigImageBitmap = (ResourcesCompat.getDrawable(resources, R.drawable.blue_flowers, null) as BitmapDrawable).bitmap
        val hackerBitmap = (ResourcesCompat.getDrawable(resources, R.drawable.hacker_icon, null) as BitmapDrawable).bitmap

        val bigPicture = NotificationCompat
            .BigPictureStyle()
            .bigPicture(bigImageBitmap)
            .setBigContentTitle("Content title")
            .setSummaryText("Summary text")

        val inboxStyle = NotificationCompat
            .InboxStyle()
            .addLine("Line1")
            .addLine("Line2")
            .addLine("Line3")
            .addLine("Line4")
            .addLine("Line5")
            .addLine("Line6")
            .addLine("Line7")
            .addLine("Line8")
            .setBigContentTitle("Bog content title")
            .setSummaryText("Summary text")

        val iNext = Intent(this@MainActivity, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this@MainActivity, 100, iNext, PendingIntent.FLAG_IMMUTABLE)

        val smallNotification = RemoteViews(packageName, R.layout.custom_notification_small)
        val largeNotification = RemoteViews(packageName, R.layout.custom_notification_large)

        smallNotification.setTextViewText(R.id.textViewHeading, "Notification heading")
        smallNotification.setTextViewText(R.id.textViewContent, "Notification content")

        largeNotification.setTextViewText(R.id.textViewHeading, "Notification heading")
        largeNotification.setTextViewText(R.id.textViewContent, "Notification content")

        notification = NotificationCompat
            .Builder(this@MainActivity, CHANNEL_ID)
            .setSmallIcon(R.drawable.hacker_icon)
            .setContentTitle("Content title")
            .setContentText("Content text")
            .setAutoCancel(true)
//            .setOngoing(true)
//            .setStyle(bigPicture)
//            .setStyle(inboxStyle)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(smallNotification)
            .setCustomBigContentView(largeNotification)
            .setContentIntent(pi)
            .setLargeIcon(hackerBitmap)
            .build()
    }
}

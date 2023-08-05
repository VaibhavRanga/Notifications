package com.example.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.example.notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: Notification

    companion object {
        const val CHANNEL_ID = "Test"
        const val CHANNEL_NAME = "TestChannel"
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
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH))
        }

        //the below three line code can be written in a single line as shown in the bigPicture variable
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.img, null)
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap

        val bigPicture = (ResourcesCompat.getDrawable(resources, R.drawable.big_picture, null) as BitmapDrawable).bitmap

        val bigPictureStyle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            NotificationCompat
                .BigPictureStyle()
                .bigPicture(bigPicture)
                .bigLargeIcon(bitmap)
                .setBigContentTitle("Full image")
                .setContentDescription("Image from Raman")
                .setSummaryText("Image summary")
        } else {
            NotificationCompat
                .BigPictureStyle()
                .bigPicture(bigPicture)
                .bigLargeIcon(bitmap)
                .setBigContentTitle("Full image")
                .setSummaryText("Image from Raman")
        }

        notification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            .setContentTitle("Raman")
            .setContentText("Hello")
            .setSubText("New message")
            .setSmallIcon(R.drawable.img)
            .setLargeIcon(bitmap)
            .setStyle(bigPictureStyle)
            .build()
    }
}

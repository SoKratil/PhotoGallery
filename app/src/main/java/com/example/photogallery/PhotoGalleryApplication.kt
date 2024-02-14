package com.example.photogallery

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.room.Room
import com.example.photogallery.db.PhotoGalleryDatabase

const val NOTIFICATION_CHANNEL_ID = "flickr_poll"

class PhotoGalleryApplication : Application() {

    // Создание экземпляра базы данных
    lateinit var database: PhotoGalleryDatabase

    override fun onCreate() {
        super.onCreate()
        // Создание канала уведомлений (если версия Android >= Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Инициализация базы данных
        database = Room.databaseBuilder(applicationContext, PhotoGalleryDatabase::class.java, "photo_gallery_database").build()
        Log.d("PhotoGalleryApplication", "Database initialized successfully")
    }
}

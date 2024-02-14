package com.example.photogallery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhotoDetail::class], version = 1)
abstract class PhotoGalleryDatabase : RoomDatabase() {

    abstract fun photoDetailDao(): PhotoDetailDao

    companion object {
        @Volatile
        private var INSTANCE: PhotoGalleryDatabase? = null

        fun getInstance(context: Context): PhotoGalleryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoGalleryDatabase::class.java,
                    "photo_gallery_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



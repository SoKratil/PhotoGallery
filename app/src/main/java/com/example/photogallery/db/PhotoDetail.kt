package com.example.photogallery.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class    PhotoDetail(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val url: String
)
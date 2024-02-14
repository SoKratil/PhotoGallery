package com.example.photogallery.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photoDetail: PhotoDetail)

    @Query("SELECT * FROM PhotoDetail")
    fun getAllPhoto(): List<PhotoDetail>

    @Query("DELETE FROM PhotoDetail")
     fun deleteAllPhoto()
}

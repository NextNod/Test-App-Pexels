package com.test.pexels.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.pexels.data.model.photo.Photo
import com.test.pexels.data.model.photo.PhotoSource
import com.test.pexels.data.room.dao.PhotoDao

@Database(entities = [Photo::class, PhotoSource::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}
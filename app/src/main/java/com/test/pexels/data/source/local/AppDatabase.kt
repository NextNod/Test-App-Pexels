package com.test.pexels.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.domain.models.photo.PhotoSource
import com.test.pexels.data.source.local.dao.PhotoDao

@Database(entities = [Photo::class, PhotoSource::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}
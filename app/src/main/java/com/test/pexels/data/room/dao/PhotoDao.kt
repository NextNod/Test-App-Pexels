package com.test.pexels.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.test.pexels.data.model.photo.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert suspend fun insert(photo : Photo)
    @Delete suspend fun delete(photo : Photo)
    @Query("SELECT * FROM Photo WHERE id = :id") suspend fun exist(id : Int) : List<Photo>
    @Query("SELECT * FROM Photo") fun getAllBookmarks() : Flow<List<Photo>>
}
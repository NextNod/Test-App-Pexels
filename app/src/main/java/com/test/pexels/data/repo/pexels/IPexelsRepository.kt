package com.test.pexels.data.repo.pexels

import com.test.pexels.data.model.collection.FeaturedCollection
import com.test.pexels.data.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface IPexelsRepository {
    suspend fun getFeaturedCollections() : List<FeaturedCollection>
    suspend fun getDefaultPhotos() : List<Photo>

    suspend fun getPhotosByQuery(query : String) : List<Photo>
    suspend fun getPhotosByCollection(collectionId : String) : List<Photo>

    suspend fun addBookmark(photo : Photo)
    suspend fun hasBookmark(photo : Photo) : Boolean
    suspend fun removeBookmark(photo : Photo)
    fun getBookmarks() : Flow<List<Photo>>
}
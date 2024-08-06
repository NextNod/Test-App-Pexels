package com.test.pexels.data.repo.pexels

import com.test.pexels.data.model.BasePaginationPage
import com.test.pexels.data.model.collection.FeaturedCollection
import com.test.pexels.data.model.photo.Photo
import kotlinx.coroutines.flow.Flow

interface IPexelsRepository {
    suspend fun getFeaturedCollections() : List<FeaturedCollection>
    suspend fun getDefaultPagination(page : Int = 0) : BasePaginationPage<Photo>

    suspend fun getPhotosByQuery(query : String) : List<Photo>
    suspend fun getPageByQuery(query : String, page : Int) : BasePaginationPage<Photo>
    suspend fun getPhotosByCollection(collectionId : String) : List<Photo>
    suspend fun getPageByCollection(collectionId : String, page : Int) : BasePaginationPage<Photo>

    suspend fun addBookmark(photo : Photo)
    suspend fun hasBookmark(photo : Photo) : Boolean
    suspend fun removeBookmark(photo : Photo)
    fun getBookmarks() : Flow<List<Photo>>
}
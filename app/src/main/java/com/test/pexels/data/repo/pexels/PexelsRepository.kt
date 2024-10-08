package com.test.pexels.data.repo.pexels

import android.content.Context
import androidx.room.Room
import com.test.pexels.data.source.remote.PexelsApi
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.data.source.remote.retrofit.Retrofit
import com.test.pexels.data.source.remote.retrofit.bodyOrError
import com.test.pexels.data.source.local.AppDatabase
import com.test.pexels.data.source.local.dao.PhotoDao
import com.test.pexels.domain.repo.IPexelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PexelsRepository(context : Context) : IPexelsRepository {
    private val pexelsApi = Retrofit.base.create(PexelsApi::class.java)
    private val photoDao : PhotoDao

    init {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build().apply {
            photoDao = photoDao()
        }
    }

    override suspend fun getFeaturedCollections() = withContext(Dispatchers.IO) {
        pexelsApi.getFeaturedCollections().bodyOrError().values
    }

    override suspend fun getDefaultPagination(page: Int) = withContext(Dispatchers.IO) {
        pexelsApi.getDefaultPhotos(page).bodyOrError()
    }

    override suspend fun getPhotosByQuery(query: String) = withContext(Dispatchers.IO) {
        pexelsApi.searchPhoto(query).bodyOrError().values
    }

    override suspend fun getPageByQuery(query: String, page: Int) = withContext(Dispatchers.IO) {
        pexelsApi.searchPhoto(query, page).bodyOrError()
    }

    override suspend fun getPhotosByCollection(collectionId: String) = withContext(Dispatchers.IO) {
        pexelsApi.getPhotosByCollection(collectionId).bodyOrError().values
    }

    override suspend fun getPageByCollection(collectionId: String, page: Int) = withContext(Dispatchers.IO) {
        pexelsApi.getPhotosByCollection(collectionId, page).bodyOrError()
    }

    override suspend fun addBookmark(photo: Photo) = withContext(Dispatchers.IO) {
        photoDao.insert(photo)
    }

    override suspend fun hasBookmark(photo: Photo) = withContext(Dispatchers.IO) {
        photoDao.exist(photo.id).isNotEmpty()
    }

    override suspend fun removeBookmark(photo: Photo) = withContext(Dispatchers.IO) {
        photoDao.delete(photo)
    }

    override fun getBookmarks() = photoDao.getAllBookmarks()
}

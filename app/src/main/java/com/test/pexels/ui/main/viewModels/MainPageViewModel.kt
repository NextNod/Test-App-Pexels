package com.test.pexels.ui.main.viewModels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pexels.data.model.collection.FeaturedCollection
import com.test.pexels.data.model.photo.Photo
import com.test.pexels.di.Dagger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPageViewModel: ViewModel() {

    private val pexelsRepository = Dagger.appComponent.providePexelsRepository()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable { updateList() }
    private val mSearchQuery = mutableStateOf("")
    private val mSelectedCollection = mutableStateOf<FeaturedCollection?>(null)
    private val mIsLoading = mutableStateOf(false)
    private val mIsError = mutableStateOf(false)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mIsLoading.value = false
        mIsError.value = true
        Log.e("Bruh", "Bruh", throwable)
    }


    val searchQuery by mSearchQuery
    val selectedCollection by mSelectedCollection
    val isLoading by mIsLoading
    val isError by mIsError
    val featuredCollection = mutableStateListOf<FeaturedCollection>()
    val defaultPhotos = mutableStateListOf<Photo>()
    val queriedPhotos = mutableStateListOf<Photo>()

    init { init() }

    fun tryAgain() {
        mIsLoading.value = true
        if(searchQuery.isNotEmpty()) setNewSearchQuery(searchQuery)
        else init()
    }

    fun setNewSearchQuery(newSearchQuery : String, immediate : Boolean = false) {
        val collection = featuredCollection.find { it.title.equals(newSearchQuery, true) }
        if(collection != null) {
            selectCollection(collection)
        } else {
            if(newSearchQuery.isNotEmpty()) mIsLoading.value = true
            mSearchQuery.value = newSearchQuery
            mSelectedCollection.value = null
            handler.removeCallbacks(runnable)
            if(!immediate) handler.postDelayed(runnable, 3000)
            else updateList()
        }
    }

    fun selectCollection(collection : FeaturedCollection) {
        mSelectedCollection.value = collection
        mSearchQuery.value = collection.title
        viewModelScope.launch(exceptionHandler) {
            mIsLoading.value = true
            pexelsRepository.getPhotosByCollection(collection.id).also {
                queriedPhotos.clear()
                queriedPhotos.addAll(it)
            }
            delay(1000)
            successQuery()
        }
    }

    private fun init() {
        viewModelScope.launch(exceptionHandler) {
            mIsLoading.value = true
            if(featuredCollection.isEmpty()) async {
                featuredCollection.addAll(pexelsRepository.getFeaturedCollections())
            }
            if(defaultPhotos.isEmpty()) async {
                defaultPhotos.addAll(pexelsRepository.getDefaultPhotos())
            }
            delay(1000)
            successQuery()
        }
    }

    private fun updateList() {
        if(searchQuery.isEmpty()) {
            queriedPhotos.clear()
            mIsLoading.value = false
        }
        else {
            viewModelScope.launch(exceptionHandler) {
                pexelsRepository.getPhotosByQuery(searchQuery).also {
                    queriedPhotos.clear()
                    queriedPhotos.addAll(it)
                }
                delay(1000)
                successQuery()
            }
        }
    }

    private fun successQuery() {
        mIsLoading.value = false
        mIsError.value = false
    }
}
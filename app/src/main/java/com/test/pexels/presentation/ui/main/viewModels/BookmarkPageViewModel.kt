package com.test.pexels.presentation.ui.main.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pexels.Dagger
import kotlinx.coroutines.launch

class BookmarkPageViewModel : ViewModel() {

    private val pexelsRepository = Dagger.appComponent.providePexelsRepository()
    private var mIsEmpty = mutableStateOf(false)

    val isEmpty by mIsEmpty
    val bookmarks = pexelsRepository.getBookmarks()

    init {
        viewModelScope.launch {
            pexelsRepository.getBookmarks().collect {
                mIsEmpty.value = it.isEmpty()
            }
        }
    }
}
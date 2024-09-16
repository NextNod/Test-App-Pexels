package com.test.pexels.presentation.ui.detail.viewModel

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.Dagger
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class DetailViewModel(
    private val photo : Photo
) : ViewModel() {

    private val pexelsRepository = Dagger.appComponent.providePexelsRepository()
    var hasBookmark by mutableStateOf<Boolean?>(null)

    init {
        if(hasBookmark == null) viewModelScope.launch {
            hasBookmark = pexelsRepository.hasBookmark(photo)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = this

    fun downloadPhoto(context : Context) {
        viewModelScope.launch {
            if(SDK_INT > Q || checkPermission(context).single())
                context.getSystemService(DownloadManager::class.java).enqueue(
                    DownloadManager.Request(Uri.parse(photo.src.original))
                        .setVisibleInDownloadsUi(true)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, photo.src.original.split("/").last())
                )
        }
    }

    fun updateBookmark() {
        viewModelScope.launch {
            if(hasBookmark!!) pexelsRepository.removeBookmark(photo)
            else pexelsRepository.addBookmark(photo)
            hasBookmark = !(hasBookmark!!)
        }
    }

    private fun checkPermission(context : Context) = callbackFlow {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : BasePermissionListener() {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    trySend(true)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    trySend(false)
                }
            }).check()
    }

    class Factory(private val photo : Photo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(photo) as T
        }
    }
}
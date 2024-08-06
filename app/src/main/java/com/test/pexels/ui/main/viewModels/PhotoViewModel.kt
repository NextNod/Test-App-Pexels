package com.test.pexels.ui.main.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel() {
    val data = mutableStateMapOf<String, Bitmap>()
}
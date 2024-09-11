package com.test.pexels.presentation.ui.main.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.presentation.core.utils.shimmerBrush
import com.test.pexels.presentation.ui.main.viewModels.PhotoViewModel

@Composable
fun MainPhotoItem(
    photo : Photo,
    modifier: Modifier,
    viewModel : PhotoViewModel = viewModel(),
    navigateToDetails : (Photo) -> Unit
) {
    var height by rememberSaveable(photo.src.large) { mutableIntStateOf((150..200).random()) }
    val context = LocalContext.current

    LaunchedEffect(photo.src.large) {
        if(viewModel.data[photo.src.large] == null) {
            Glide.with(context)
                .asBitmap()
                .load(photo.src.large)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) { viewModel.data[photo.src.large] = resource }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .then(modifier)
            .animateContentSize()
            .clickable { navigateToDetails(photo) }
    ) {
        Crossfade(
            targetState = viewModel.data[photo.src.large], label = ""
        ) {
            if(it != null) {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .onSizeChanged { height = it.height }
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(height.dp)
                        .background(shimmerBrush())
                )
            }
        }
    }
}
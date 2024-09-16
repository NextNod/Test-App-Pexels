package com.test.pexels.presentation.ui.detail

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.test.pexels.R
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.presentation.core.frag.BaseFragment
import com.test.pexels.presentation.ui.detail.viewModel.DetailViewModel
import com.test.pexels.presentation.core.utils.Colors
import com.test.pexels.presentation.ui.detail.components.DetailContent

class DetailsFragment : BaseFragment() {

    private val photo by lazy {
        requireArguments().run {
            if (SDK_INT >= TIRAMISU) {
                getParcelable(KEY_PHOTO, Photo::class.java)!!
            } else {
                getParcelable(KEY_PHOTO)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(inflater.context).apply {
        setContent {
            val viewModel by viewModel<DetailViewModel>(factory = DetailViewModel.Factory(photo))
            DetailContent(
                photo = photo,
                hasBookmark = viewModel.hasBookmark == true,
                downloadPhoto = { viewModel.downloadPhoto(requireContext()) },
                updateBookmark = viewModel::updateBookmark,
                goBack = getRouter()::exit
            )
        }
    }

    companion object {
        private const val KEY_PHOTO = "KEY_PHOTO"

        fun newInstance(photo : Photo) = DetailsFragment().also {
            it.arguments = bundleOf(
                KEY_PHOTO to photo
            )
        }
    }
}
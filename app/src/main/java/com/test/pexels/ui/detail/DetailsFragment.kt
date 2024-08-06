package com.test.pexels.ui.detail

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.test.pexels.R
import com.test.pexels.data.model.photo.Photo
import com.test.pexels.ui.base.frag.BaseFragment
import com.test.pexels.ui.detail.viewModel.DetailViewModel
import com.test.pexels.utils.ui.Colors

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
    ) = ComposeView(inflater.context).apply { setContent { Root() } }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable private fun Root(
        viewModel : DetailViewModel = viewModel(factory = DetailViewModel.Factory(photo))
    ) {
        Column(
            modifier = Modifier
                .background(Colors.background())
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            // Header
            Box(
                modifier = Modifier.padding(top = 18.dp, bottom = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Colors.onContainer()),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = Colors.container())
                            .clickable { getRouter().exit() }
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Text(
                    text = photo.photographer,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.mulish_bold)),
                        color = Colors.onContainer(),
                        fontSize = 18.sp
                    )
                )
            }

            // Main image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    model = photo.src.original,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    transition = CrossFade(tween()),
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                )
            }

            // Footer
            Row(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .navigationBarsPadding()
            ) {
                // Download button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = Colors.container())
                        .clickable { viewModel.downloadPhoto(requireContext()) }
                        .padding(end = 38.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = null,
                        contentScale = ContentScale.None,
                        colorFilter = ColorFilter.tint(Colors.onPrimary()),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color = Colors.primary())
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = stringResource(R.string.download),
                        fontFamily = FontFamily(Font(R.font.mulish_semibold)),
                        color = Colors.onContainer()
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                // Bookmark button
                val iconColor by animateColorAsState(
                    if(viewModel.hasBookmark == true) Colors.onPrimary() else Colors.onContainer()
                )
                val backgroundColor by animateColorAsState(
                    if(viewModel.hasBookmark == true) Colors.primary() else Colors.container()
                )
                Image(
                    painter = painterResource(R.drawable.ic_bookmark_unselected),
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    colorFilter = ColorFilter.tint(iconColor),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .clickable { viewModel.updateBookmark() }
                )
            }
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
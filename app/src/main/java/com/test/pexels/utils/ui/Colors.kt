package com.test.pexels.utils.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.test.pexels.R

object Colors {
    @Composable fun primary() = colorResource(R.color.red)
    @Composable fun onPrimary() = colorResource(R.color.white)
    @Composable fun secondary() = if(isSystemInDarkTheme()) colorResource(R.color.night_dark_gray) else colorResource(R.color.dark_gray)
    @Composable fun container() = if(isSystemInDarkTheme()) colorResource(R.color.night_gray) else colorResource(R.color.gray)
    @Composable fun onContainer() = if(isSystemInDarkTheme()) colorResource(R.color.white) else colorResource(R.color.black)
    @Composable fun onContainerInvent() = colorResource(R.color.white)
    @Composable fun background() = if(isSystemInDarkTheme()) colorResource(R.color.black) else colorResource(R.color.white)

    /*
    @Composable fun red() = colorResource(R.color.red)
    @Composable fun white() = colorResource(R.color.white)
    @Composable fun gray() = if(isSystemInDarkTheme()) colorResource(R.color.night_gray) else colorResource(R.color.gray)
    @Composable fun dark_gary() = if(isSystemInDarkTheme()) colorResource(R.color.night_dark_gray) else colorResource(R.color.dark_gray)
    @Composable fun black() = colorResource(R.color.black)
    */
}
package com.test.pexels.presentation.ui

import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.presentation.ui.detail.DetailsFragment
import com.test.pexels.presentation.ui.main.MainFragment
import com.test.pexels.presentation.core.navigation.fragmentScreen

object Screens {

    fun main() = fragmentScreen {
        MainFragment()
    }

    fun detail(photo : Photo) = fragmentScreen {
        DetailsFragment.newInstance(photo)
    }
}
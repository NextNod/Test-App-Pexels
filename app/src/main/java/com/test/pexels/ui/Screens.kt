package com.test.pexels.ui

import com.test.pexels.data.model.photo.Photo
import com.test.pexels.ui.detail.DetailsFragment
import com.test.pexels.ui.main.MainFragment
import com.test.pexels.utils.navigation.fragmentScreen

object Screens {

    fun main() = fragmentScreen {
        MainFragment()
    }

    fun detail(photo : Photo) = fragmentScreen {
        DetailsFragment.newInstance(photo)
    }
}
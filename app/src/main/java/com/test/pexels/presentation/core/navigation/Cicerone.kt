package com.test.pexels.presentation.core.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun fragmentScreen(creator: Creator<FragmentFactory, Fragment>): FragmentScreen {
    return FragmentScreen(fragmentCreator = creator)
}

fun activityScreen(creator: Creator<Context, Intent>): ActivityScreen {
    return ActivityScreen(intentCreator = creator)
}
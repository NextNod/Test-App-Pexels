package com.test.pexels.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.terrakok.cicerone.Replace
import com.test.pexels.R
import com.test.pexels.ui.Screens
import com.test.pexels.ui.base.BaseActivity
import com.test.pexels.utils.navigation.RouterProvider

class MainActivity : BaseActivity(), RouterProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set status bar transparent
        //window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)

        // Set content view
        setContentView(R.layout.activity_main)

        // Init main screen
        navigator?.applyCommands(arrayOf(Replace(Screens.main())))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigator?.let { navigatorHolder.setNavigator(it) }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun getContainerId() = R.id.container
    override fun getRouter() = mRouter
}
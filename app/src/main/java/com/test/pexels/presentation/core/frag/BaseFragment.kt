package com.test.pexels.presentation.core.frag

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.test.pexels.presentation.core.navigation.RouterProvider
import com.test.pexels.Dagger
import com.test.pexels.presentation.core.navigation.BackButtonListener

open class BaseFragment: Fragment(), BackButtonListener {

    open fun getRouter(): Router {

        parentFragment?.let {
            if (it is RouterProvider)
                return it.getRouter()
        }

        activity?.let {
            if (it is RouterProvider) {
                return it.getRouter()
            }
        }

        throw IllegalStateException("No router")
    }

    open fun getActivityRouter(): Router {
        activity?.let {
            if (it is RouterProvider) {
                return it.getRouter()
            }
        }
        throw IllegalStateException("No router")
    }

    override fun onBackPressed(): Boolean {
        getRouter().exit()
        return true
    }

    val prefs = Dagger.appComponent.providePreferencesRepository()
}
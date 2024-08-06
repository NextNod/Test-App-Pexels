package com.test.pexels.ui.base.frag

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.test.pexels.utils.navigation.RouterProvider
import com.test.pexels.di.Dagger
import com.test.pexels.utils.navigation.BackButtonListener

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
package com.test.pexels.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.test.pexels.utils.navigation.RouterProvider
import com.test.pexels.data.repo.prefs.IPreferencesRepository
import com.test.pexels.di.Dagger
import com.test.pexels.utils.navigation.BackButtonListener
import com.test.pexels.utils.navigation.MainNavigator
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    @Inject lateinit var mRouter: Router
    @Inject lateinit var prefs: IPreferencesRepository

    var navigatorHolder: NavigatorHolder = Dagger.appComponent.provideNavigatorHolder()

    var navigator: MainNavigator? = null
        get() {
            if (field == null) {
                field = object : MainNavigator(this, getContainerId()) {}
            }
            return field
        }

    abstract fun getContainerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.appComponent.inject(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val fragment = supportFragmentManager.findFragmentById(getContainerId())
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            (this as RouterProvider).getRouter().exit()
            return
        }
    }
}
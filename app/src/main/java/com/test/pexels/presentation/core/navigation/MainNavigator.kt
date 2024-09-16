package com.test.pexels.presentation.core.navigation

import androidx.fragment.app.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.test.pexels.R

open class MainNavigator (
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {
    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        fragmentTransaction.setCustomAnimations(
            R.animator.slide_in,
            R.animator.slide_out,
            R.animator.exit_in,
            R.animator.exit_out,
        )
    }
}
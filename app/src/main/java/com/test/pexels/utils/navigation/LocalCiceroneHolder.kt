package com.test.pexels.utils.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class LocalCiceroneHolder {

    private var containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerTag: String): Cicerone<Router> {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]!!
    }
}

package com.test.pexels.data.repo.prefs

import android.content.Context
import android.content.SharedPreferences
import com.test.pexels.domain.repo.IPreferencesRepository

class PreferencesRepository(context: Context) : IPreferencesRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    object Keys {
        const val ACCOUNT_ID = "ACCOUNT_ID"
        const val ACCOUNT_NAME = "ACCOUNT_NAME"
        const val ACCOUNT_IMAGE = "ACCOUNT_IMAGE"
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
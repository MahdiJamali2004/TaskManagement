package com.solo.tasky.feature_login.data

import android.content.Context
import android.content.SharedPreferences
import com.solo.tasky.feature_task.data.util.Constants

class LoginSharedPreferences(private val context: Context) {
    fun getLoginSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCES,Context.MODE_PRIVATE)
    }
}
package com.solo.tasky.feature_login.data

import com.solo.tasky.feature_login.domain.repository.LoginRepository
import com.solo.tasky.feature_task.data.util.Constants
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginSharedPreferences: LoginSharedPreferences
) : LoginRepository {
    override fun getUsername(): String {

        val username = loginSharedPreferences.getLoginSharedPreferences()
            .getString(Constants.usernameKey, "")
        return username!!
    }

    override suspend fun insertUsername(username: String) {
        loginSharedPreferences.getLoginSharedPreferences().edit()
            .putString(Constants.usernameKey, username).apply()
    }

    override fun getGmail(): String {
        val gmail = loginSharedPreferences.getLoginSharedPreferences()
            .getString(Constants.gmailKey, "")
        return gmail!!
    }

    override suspend fun  insertGmail(gmail: String) {
        loginSharedPreferences.getLoginSharedPreferences().edit()
            .putString(Constants.gmailKey, gmail).apply()
    }


}
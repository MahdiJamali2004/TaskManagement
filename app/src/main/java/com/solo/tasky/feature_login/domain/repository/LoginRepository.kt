package com.solo.tasky.feature_login.domain.repository

interface LoginRepository {

    fun getUsername() : String
    suspend fun insertUsername(username:String)
    fun getGmail() : String
    suspend fun insertGmail(gmail : String)
}
package com.solo.tasky.feature_login.presentation

data class LoginTextState(
    val text : String = "",
    val errorText : String? =  null ,
    val isFocus : Boolean = false
)

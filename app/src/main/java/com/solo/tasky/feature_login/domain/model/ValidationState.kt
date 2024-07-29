package com.solo.tasky.feature_login.domain.model

data class ValidationState (
    val isSuccessful : Boolean,
    val textError : String?
)
package com.solo.tasky.feature_login.domain.login_use_cases

import android.util.Patterns
import com.solo.tasky.feature_login.domain.model.ValidationState

class InsertGmailUseCase {
     fun execute(gmail: String): ValidationState {


        if (gmail.isBlank())
            return ValidationState(
                isSuccessful = false,
                textError = "gmail is empty.please enter your email address."
            )

        if (!Patterns.EMAIL_ADDRESS.matcher(gmail).matches())
            return ValidationState(
                isSuccessful = false,
                textError = "please enter valid gmail."
            )

        return ValidationState(true,null)


    }

}


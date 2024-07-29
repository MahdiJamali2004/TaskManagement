package com.solo.tasky.feature_login.domain.login_use_cases

import com.solo.tasky.feature_login.domain.model.ValidationState


class InsertUserNameUseCase{
     fun execute(username: String): ValidationState {


        if (username.isBlank())
            return ValidationState(
                false, "gmail is empty.please enter your email address."
            )
        return ValidationState(true, null)


    }

}


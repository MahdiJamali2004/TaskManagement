package com.solo.tasky.feature_login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_login.domain.login_use_cases.InsertGmailUseCase
import com.solo.tasky.feature_login.domain.login_use_cases.InsertUserNameUseCase
import com.solo.tasky.feature_login.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val insertGmailUseCase: InsertGmailUseCase,
    private val insertUserNameUseCase: InsertUserNameUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {

    var gmailLoginState by mutableStateOf(LoginTextState())
        private set

    var userNameLoginState by mutableStateOf(LoginTextState())
        private set

    private var _submit = MutableSharedFlow<Boolean>()
    val submit = _submit.asSharedFlow()

    var userInformationInserted = MutableStateFlow(false)


    init {


        viewModelScope.launch(Dispatchers.IO) {
            val username = loginRepository.getUsername()
            val gmail = loginRepository.getGmail()

            if (gmail.isNotEmpty() && username.isNotEmpty())
                userInformationInserted.value = true
        }


    }


    fun onActions(actions: Actions) {
        when (actions) {

            is Actions.GmailChange -> {
                gmailLoginState = gmailLoginState.copy(
                    text = actions.text
                )
            }

            is Actions.UserNameChange -> {
                userNameLoginState = userNameLoginState.copy(
                    text = actions.text
                )
            }


            Actions.Validate -> {

                validate()

            }

            is Actions.FocusStateGmailChange -> {
                gmailLoginState = gmailLoginState.copy(
                    isFocus = actions.focusState.isFocused
                )

            }

            is Actions.FocusStateUserNameChange -> {
                userNameLoginState = userNameLoginState.copy(
                    isFocus = actions.focusState.isFocused
                )

            }
        }

    }


    private fun validate() {
        val insertGmail = insertGmailUseCase.execute(gmailLoginState.text)
        val insertUsername = insertUserNameUseCase.execute(userNameLoginState.text)

        val results = listOf(insertGmail, insertUsername)
        val hasError = results.any { !it.isSuccessful }
        if (hasError) {
            gmailLoginState = gmailLoginState.copy(
                errorText = insertGmail.textError
            )
            userNameLoginState = userNameLoginState.copy(
                errorText = insertUsername.textError
            )
            return
        }
        viewModelScope.launch {
            loginRepository.insertGmail(gmailLoginState.text)
            loginRepository.insertUsername(userNameLoginState.text)

            _submit.emit(true)

        }

    }


}
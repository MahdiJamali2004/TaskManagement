package com.solo.tasky.feature_login.presentation

import androidx.compose.ui.focus.FocusState

sealed class Actions {
    data class GmailChange(val text : String) : Actions()
    data class UserNameChange(val text : String) : Actions()
    data class FocusStateGmailChange(val focusState: FocusState) : Actions()
    data class FocusStateUserNameChange(val focusState: FocusState) : Actions()
    data object Validate  : Actions()
}
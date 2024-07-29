package com.solo.tasky.feature_login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.feature_login.presentation.LoginTextState
import com.solo.tasky.feature_login.presentation.TaskAnimation

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSubmitClick: () -> Unit,
    gmailState: LoginTextState,
    onGmailChange: (text: String) -> Unit,
    onFocusGmailChange: (focusState: FocusState) -> Unit,
    userNameState: LoginTextState,
    onUserNameChange: (text: String) -> Unit,
    onFocusUserNameChange: (focusState: FocusState) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        TaskAnimation(
            isClickableAnimation = false,
            modifier = Modifier.size(260.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Welcome to Tasky",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Sign In to continue",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomOutlineTextField(
            modifier = Modifier,
            value = gmailState.text,
            onValueChange = onGmailChange,
            label = "gmail",
            focusState = gmailState.isFocus,
            trailingIconDefault = Icons.Outlined.Email,
            trailingIconField = Icons.Filled.Email,
            placeholder = "Enter your gmail",
            errorText = gmailState.errorText,
            onFocusChange = onFocusGmailChange,
            keyboardType = KeyboardType.Email,
            isError = (gmailState.errorText != null)
        )

        CustomOutlineTextField(
            modifier = Modifier,
            value = userNameState.text,
            onValueChange = onUserNameChange,
            label = "username",
            focusState = userNameState.isFocus,
            trailingIconDefault = Icons.Outlined.Person,
            trailingIconField = Icons.Filled.Person,
            placeholder = "Enter your username",
            errorText = userNameState.errorText,
            onFocusChange = onFocusUserNameChange,
            isError = (userNameState.errorText != null)
        )

        Button(
            onClick = onSubmitClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {

            Text(
                text = "Sign In to continue",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }


}

@Preview
@Composable
fun ShowLoginScreen(

) {

    LoginScreen(
        gmailState = LoginTextState(text = "test@gmail.com"),
        onGmailChange = {},
        onFocusGmailChange = {},
        userNameState = LoginTextState(text = "Destiny"),
        onUserNameChange = {},
        onFocusUserNameChange = {},
        onSubmitClick = {}
    )
}
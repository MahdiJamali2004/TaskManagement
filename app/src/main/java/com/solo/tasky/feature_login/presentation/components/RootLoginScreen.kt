package com.solo.tasky.feature_login.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_login.presentation.Actions
import com.solo.tasky.feature_login.presentation.LoginScreenViewModel
import com.solo.tasky.feature_task.presentation.util.Screen

@Composable
fun RootLoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    onLoginScreenDone : (String) -> Unit,
    navController: NavController
) {

    val submitState = loginScreenViewModel.submit.collectAsState(initial = false)
    val userInformationInserted = loginScreenViewModel.userInformationInserted.collectAsState()

    LaunchedEffect(key1 = submitState.value) {

            if (submitState.value) {
               // navController.navigate(Screen.TaskHomeScreen.route)
                onLoginScreenDone(Screen.TaskHomeScreen.route)
            }

    }
    if (!userInformationInserted.value) {

        LoginScreen(
            gmailState = loginScreenViewModel.gmailLoginState,
            onGmailChange = {
                loginScreenViewModel.onActions(Actions.GmailChange(it))
            },
            onFocusGmailChange = {
                loginScreenViewModel.onActions(Actions.FocusStateGmailChange(it))

            },
            userNameState = loginScreenViewModel.userNameLoginState,
            onUserNameChange = {
                loginScreenViewModel.onActions(Actions.UserNameChange(it))

            },
            onFocusUserNameChange = {
                loginScreenViewModel.onActions(Actions.FocusStateUserNameChange(it))

            },
            onSubmitClick = {
                loginScreenViewModel.onActions(Actions.Validate)


            }
        )
    }
    else {
//        navController.navigate(Screen.TaskHomeScreen.route)

        onLoginScreenDone(Screen.TaskHomeScreen.route)
      //  MainScreen(startScreen = Screen.TaskHomeScreen.route)
    }

}

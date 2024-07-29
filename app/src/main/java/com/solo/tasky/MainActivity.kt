package com.solo.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.solo.tasky.feature_task.data.util.Constants
import com.solo.tasky.feature_task.presentation.util.Screen
import com.solo.tasky.ui.theme.Black
import com.solo.tasky.ui.theme.TaskManagmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = checkStartDestination()

        setContent {
            TaskManagmentTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Black  ,
                        darkIcons = false
                    )
                }
                MainScreen(startScreen = startDestination)
            }
        }
    }

    private fun checkStartDestination(): String {
        val sharedPreferences = getSharedPreferences(
            Constants.LOGIN_SHARED_PREFERENCES,
            MODE_PRIVATE
        )
        val username = sharedPreferences.getString(Constants.usernameKey, null)
        val gmail = sharedPreferences.getString(Constants.gmailKey, null)

        return if (username == null || gmail == null)
            Screen.LoginScreen.route
        else
            Screen.TaskHomeScreen.route


    }
}





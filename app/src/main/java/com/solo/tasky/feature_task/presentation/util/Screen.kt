package com.solo.tasky.feature_task.presentation.util


sealed class Screen(val route :String) {
    object TaskCalScreen : Screen("taskCalScreen")
    object TaskHomeScreen : Screen("task_home_screen")
    object TaskAddEdit : Screen("taskAddEdit")
    object LoginScreen : Screen("loginScreen")
    object NoteScreen : Screen("noteScreen")
    object NoteAddEditScreen : Screen("noteAddEditScreen")

}
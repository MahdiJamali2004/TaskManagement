package com.solo.tasky

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.solo.tasky.R
import com.solo.tasky.feature_login.presentation.components.RootLoginScreen
import com.solo.tasky.feature_note.domain.model.NoteType
import com.solo.tasky.feature_note.presentation.addEditNote.component.RootAddEditNoteScreen
import com.solo.tasky.feature_note.presentation.noteScreen.components.NoteScreenRoot
import com.solo.tasky.feature_task.presentation.taskCalendar.Component.RootTakCalScreen
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.TaskAddEditScreen
import com.solo.tasky.feature_task.presentation.taskHome.component.RootHomeTaskScreen
import com.solo.tasky.feature_task.presentation.util.Screen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    startScreen: String
) {

    var startScreenState by rememberSaveable {
        mutableStateOf(startScreen)
    }
    val navController = rememberNavController()

    if (Screen.LoginScreen.route == startScreenState)
        RootLoginScreen(
            navController = navController,
            onLoginScreenDone = {
                startScreenState = Screen.TaskHomeScreen.route
            }
        )
    else {

        Scaffold(modifier.fillMaxSize(),
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                NavigationBar(
                ) {
                    NavigationBarItem(
                        selected = currentDestination == Screen.TaskHomeScreen.route,
                        onClick = {
//                            navController.navigate(Screen.TaskHomeScreen.route)
                            // selectedScreen = Screen.TaskHomeScreen.route
                            navController.navigate(Screen.TaskHomeScreen.route) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = "Home")
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentDestination == Screen.TaskHomeScreen.route) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "HomeScreen"
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = currentDestination == Screen.TaskCalScreen.route,
                        onClick = {
//                            navController.navigate(Screen.TaskCalScreen.route)
                            //    selectedScreen = Screen.TaskCalScreen.route
                            navController.navigate(Screen.TaskCalScreen.route) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        label = {
                            Text(text = "Calendar")
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentDestination == Screen.TaskHomeScreen.route) Icons.Outlined.DateRange else Icons.Filled.DateRange,
                                contentDescription = "Calendar"
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = currentDestination == Screen.NoteScreen.route,
                        onClick = {

                            navController.navigate(Screen.NoteScreen.route) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        label = {
                            Text(text = "Note")
                        },
                        icon = {
                            Icon(
                                painterResource(id = if (currentDestination == Screen.NoteScreen.route) R.drawable.ic_notepad_filled else R.drawable.ic_notepad_outline),
                                contentDescription = "Note",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                }
            }
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Screen.TaskHomeScreen.route
            ) {
                composable(Screen.TaskCalScreen.route) {
                    RootTakCalScreen(navController = navController)


                }

                composable(
                    Screen.TaskAddEdit.route + "?taskId={taskId}&taskDate={taskDate}",
                    arguments = listOf(
                        navArgument(
                            "taskId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            "taskDate"
                        ) {
                            type = NavType.StringType
                            defaultValue = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("MMM, yyyy EEE dd"))
                        }
                    )
                ) {
                    TaskAddEditScreen(
                        navController = navController
                    )
                }

                composable(Screen.LoginScreen.route) {
                    RootLoginScreen(
                        navController = navController,
                        onLoginScreenDone = {
                            navController.popBackStack()
                            navController.navigate(Screen.TaskHomeScreen.route)
                        })
                }
                composable(Screen.TaskHomeScreen.route) {
                    RootHomeTaskScreen(navController = navController)
                }

                composable(Screen.NoteScreen.route) {
                    val noteType = navController.currentBackStackEntry?.savedStateHandle?.get<String>("noteType")
                    NoteScreenRoot(navController = navController , receivedNoteType = noteType)
                }
                composable(Screen.NoteAddEditScreen.route+"?noteId={noteId}&noteColor={noteColor}&noteType={noteType}", arguments = listOf(
                    navArgument(
                        name = "noteId"
                    ){
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "noteColor"
                    ){
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "noteType"
                    ){
                        type = NavType.StringType
                        defaultValue = NoteType.ALL
                    }
                )){navBackStack ->
                     val noteColor = navBackStack.arguments?.getInt("noteColor") ?: -1
                    RootAddEditNoteScreen(navController = navController, noteColor = noteColor)
                }

            }


        }
    }

}

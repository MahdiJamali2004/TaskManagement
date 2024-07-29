package com.solo.tasky.feature_task.presentation.taskHome.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_task.presentation.taskHome.HomeScreenEvents
import com.solo.tasky.feature_task.presentation.taskHome.TaskHomeViewModel
import com.solo.tasky.feature_task.presentation.util.Screen

@Composable
fun RootHomeTaskScreen(
    homeScreenViewModel: TaskHomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val homeScreenStates = homeScreenViewModel.homeScreenStates.collectAsState()

    HomeTaskScreen(
        homeScreenStates = homeScreenStates.value,

        onProfileClick = {
            navController.navigateUp()
        },
        onSearchFocusChange = {
            homeScreenViewModel.onEvents(HomeScreenEvents.SearchFocusChange(it))
        },
        onSearchChange = {
            homeScreenViewModel.onEvents(HomeScreenEvents.SearchChange(it))
        },
        onEditTaskClick = { id ->
            navController.navigate(Screen.TaskAddEdit.route + "?taskId=${id}&taskDate=${homeScreenStates.value.date}")

        },
        onSortClick = {
            homeScreenViewModel.onEvents(HomeScreenEvents.TasksOrderChange(it))
        },
        onAddTask = {
            navController.navigate(Screen.TaskAddEdit.route + "?taskDate=${homeScreenStates.value.date}")
        },
        onDateChange = {
            homeScreenViewModel.onEvents(HomeScreenEvents.CurrentDateChange(it))
        }
    )

}
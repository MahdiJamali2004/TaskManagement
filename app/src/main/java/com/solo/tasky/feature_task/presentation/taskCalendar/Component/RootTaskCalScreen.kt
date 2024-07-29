package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_task.presentation.taskCalendar.TaskCalEvents
import com.solo.tasky.feature_task.presentation.taskCalendar.TaskCalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RootTakCalScreen(
    navController: NavController,
    taskCalViewModel: TaskCalendarViewModel = hiltViewModel()
) {

    val taskCalState = taskCalViewModel.taskCalState.collectAsState().value
    TaskCalScreen(
        navController = navController,
        onWeekDayItemClick = { day ->
            val date = LocalDate.parse(
                taskCalState.currentDate + " " + day,
                DateTimeFormatter.ofPattern("MMM, yyyy dd")
            )
            taskCalViewModel.onEvents(TaskCalEvents.DayChange(date))
        },
        onSortClick = {
            taskCalViewModel.onEvents(TaskCalEvents.TasksOrderChange(it))

        },
        onDateChange = {
            taskCalViewModel.onEvents(TaskCalEvents.DateChange(it))
        },
        onDeleteTask = {
            taskCalViewModel.onEvents(TaskCalEvents.DeleteTask(it))
        },
        currentMonthYear = taskCalState.currentDate,
        weekDay = taskCalState.currentDay,
        currentOrder = taskCalState.order,
        listWeekDay = taskCalState.daysMonth,
        listTask = taskCalState.tasks,
        currentDate = taskCalState.currentDate + " " + taskCalState.currentDay
    )
}
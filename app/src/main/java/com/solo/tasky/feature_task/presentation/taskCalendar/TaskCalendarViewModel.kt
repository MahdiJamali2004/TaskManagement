package com.solo.tasky.feature_task.presentation.taskCalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_task.domain.task_notifications.InitializeNotificationWorker
import com.solo.tasky.feature_task.domain.usecases.TaskUseCases
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskCalendarViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val initializeNotificationWorker : InitializeNotificationWorker
) : ViewModel() {

    private var _taskCalStates = MutableStateFlow<TaskCalState>(TaskCalState())
    val taskCalState = _taskCalStates.asStateFlow()


    init {
        viewModelScope.launch {
            taskUseCases.deleteTaskUseCase.autoDeleteTask()
        }
        updateCurrentDay(LocalDate.now())
        updateDaysOfMonth(LocalDate.now())
        getTasks(taskOrder = TaskOrder.ByPriority(OrderType.Descending))

    }

    fun onEvents(taskCalEvents: TaskCalEvents) {

        when (taskCalEvents) {
            is TaskCalEvents.TasksOrderChange -> {
                getTasks(taskCalEvents.taskOrder)
            }

            is TaskCalEvents.DateChange -> {

                updateCurrentDate(taskCalEvents.date)
                updateDaysOfMonth(taskCalEvents.date)
                updateCurrentDay(taskCalEvents.date)
                getTasks()
            }

            is TaskCalEvents.DayChange -> {

                updateCurrentDay(taskCalEvents.date)
                getTasks()
            }

            is TaskCalEvents.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTaskUseCase.deleteTask(taskCalEvents.task)
                    initializeNotificationWorker.cancelTaskNotification(task = taskCalEvents.task)
                }
            }

            is TaskCalEvents.DoneTask -> {
                _taskCalStates.value = taskCalState.value.copy(
                    isDone = !taskCalEvents.task.isDone
                )
            }

        }
    }


    private fun getTasks(taskOrder: TaskOrder = taskCalState.value.order) {
        viewModelScope.launch {
            taskUseCases.getTasksUseCase.getTasksSortedBy(
                taskOrder,
                taskCalState.value.currentDate + " " + taskCalState.value.currentDay //("MMM, yyyy EEE dd")
            )
                .collectLatest {
                    _taskCalStates.value = taskCalState.value.copy(
                        tasks = it,
                        order = taskOrder
                    )
                }
        }
    }

    //Updates daysOfMonth its list of Pairs(Thu,25)
    private fun updateDaysOfMonth(date: LocalDate) {
        val pattern = DateTimeFormatter.ofPattern("EEE dd")
        viewModelScope.launch {
            taskUseCases.getTasksUseCase.getDaysOfMonth(date).map {
                it.map {
                    val stringResult =
                        LocalDate.of(it.year, it.month, it.dayOfMonth).format(pattern)
                    Pair(stringResult.substring(0, 3), stringResult.substring(4, 6))
                }
            }.collectLatest {
                _taskCalStates.value = taskCalState.value.copy(
                    daysMonth = it
                )
            }
        }

    }

    //  updateCurrentDate like May, 2022
    private fun updateCurrentDate(date: LocalDate) {
        val pattern = DateTimeFormatter.ofPattern("MMM, yyyy")
        val dateState = LocalDate.of(date.year, date.month, date.dayOfMonth).format(pattern)

        _taskCalStates.value = taskCalState.value.copy(
            currentDate = dateState
        )
    }

    private fun updateCurrentDay(date: LocalDate) {
        val pattern = DateTimeFormatter.ofPattern("EEE dd")
        val dayState = LocalDate.of(date.year, date.month, date.dayOfMonth).format(pattern)
        _taskCalStates.value = taskCalState.value.copy(
            currentDay = dayState
        )
    }

}
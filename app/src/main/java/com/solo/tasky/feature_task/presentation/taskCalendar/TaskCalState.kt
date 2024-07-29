package com.solo.tasky.feature_task.presentation.taskCalendar

import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TaskCalState(
    val tasks : List<Task> = emptyList(),
    val isDone : Boolean = false ,
    val daysMonth :List<Pair<String,String>> = emptyList(),
    val order : TaskOrder  = TaskOrder.ByPriority(OrderType.Descending),
    val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM, yyyy")),
    val currentDay : String = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE dd")),
)

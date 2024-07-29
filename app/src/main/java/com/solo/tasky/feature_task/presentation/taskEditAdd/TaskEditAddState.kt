package com.solo.tasky.feature_task.presentation.taskEditAdd

import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.models.TaskColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TaskEditAddState(
    val startTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm a")),
    val isDone: Boolean = false,
    val priority: String = "High",
    val taskColor: TaskColor = Task.taskColors.random()
//    val dateOfTask: String = LocalDateTime.now()
//        .format(DateTimeFormatter.ofPattern("MMM, yyyy EEE dd"))
)

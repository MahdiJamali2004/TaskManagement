package com.solo.tasky.feature_task.presentation.taskCalendar

import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.util.TaskOrder
import java.time.LocalDate

sealed class TaskCalEvents {
    data class TasksOrderChange(val taskOrder: TaskOrder) : TaskCalEvents()
    data class DateChange(val date : LocalDate) : TaskCalEvents()
    data class DayChange(val date : LocalDate) : TaskCalEvents()
    data class DeleteTask(val task : Task) : TaskCalEvents()
    data class DoneTask(val task : Task) : TaskCalEvents()
}
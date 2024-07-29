package com.solo.tasky.feature_task.domain.util

sealed class TaskPriority(val priority : String) {
    object HighPriority : TaskPriority("High")
    object MediumPriority : TaskPriority("Medium")
    object LowPriority : TaskPriority("Low")

}
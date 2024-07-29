package com.solo.tasky.feature_task.presentation.taskEditAdd

data class SubTaskState(
    val text : String = "" ,
    val hint : String = "Enter subTask",
    val isHintVisible : Boolean = true ,
    val isDone : Boolean = false
)

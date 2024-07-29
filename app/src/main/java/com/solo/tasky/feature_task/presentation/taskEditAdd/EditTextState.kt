package com.solo.tasky.feature_task.presentation.taskEditAdd

data class EditTextState(
    val text: String = "",
    val isHintVisible: Boolean = true,
    val hint: String = "Enter Title..."
)

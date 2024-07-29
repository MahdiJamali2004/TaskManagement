package com.solo.tasky.feature_task.presentation.taskHome

import androidx.compose.ui.focus.FocusState
import com.solo.tasky.feature_task.domain.util.TaskOrder
import java.time.LocalDate

sealed class HomeScreenEvents {
    data class SearchChange(val text: String) : HomeScreenEvents()
    data class SearchFocusChange(val focusState: FocusState) : HomeScreenEvents()
    data class CurrentDateChange(val date: LocalDate) : HomeScreenEvents()
    data class TasksOrderChange(val taskOrder: TaskOrder) : HomeScreenEvents()

}
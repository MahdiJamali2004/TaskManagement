package com.solo.tasky.feature_task.presentation.taskHome

import com.solo.tasky.feature_task.data.util.Constants
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class HomeScreenStates(
    val searchBarState: SearchBarState = SearchBarState(),
    val date : String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)),
    val tasks : List<Task> = emptyList(),
    val username : String = "",
    val order : TaskOrder = TaskOrder.ByPriority(OrderType.Descending)

)

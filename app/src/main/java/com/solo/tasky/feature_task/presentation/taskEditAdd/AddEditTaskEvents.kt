package com.solo.tasky.feature_task.presentation.taskEditAdd

import androidx.compose.ui.focus.FocusState
import com.solo.tasky.feature_task.domain.models.TaskColor
import java.time.LocalDate
import java.time.LocalTime

sealed class AddEditTaskEvents {

    data class TitleChange(val text : String) : AddEditTaskEvents()
    data class TitleFocusChange(val focusState : FocusState) : AddEditTaskEvents()
    data class DescriptionChange(val text : String) : AddEditTaskEvents()
    data class DescriptionFocusChange(val focusState : FocusState) : AddEditTaskEvents()
    data class StartTimeChange(val time : LocalTime) : AddEditTaskEvents()
    data class DateChange(val date : LocalDate) : AddEditTaskEvents()
    data class DoneClick(val isDone : Boolean) : AddEditTaskEvents()
    data class PriorityChange (val priority : String) : AddEditTaskEvents()
    object AddSubTask : AddEditTaskEvents()
    data class UpdateSubTask(val text : String , val isDone : Boolean , val index : Int) : AddEditTaskEvents()
    data class FocusStateChangeSubTask(val focusState: FocusState , val index : Int) : AddEditTaskEvents()
    data class TaskColorChange(val taskColor: TaskColor) : AddEditTaskEvents()
    object InsertTask : AddEditTaskEvents()
    data class DeleteSubTask(val index: Int) :AddEditTaskEvents()




}
package com.solo.tasky.feature_note.presentation.addEditNote


import com.solo.tasky.feature_note.domain.model.NoteType
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.models.TaskColor

data class AddEditNoteStates(
    val noteType: String = NoteType.ALL,
    val noteColor: TaskColor = Task.taskColors.random()
)
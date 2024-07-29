package com.solo.tasky.feature_note.presentation.addEditNote

import androidx.compose.ui.focus.FocusState
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_task.domain.models.TaskColor

sealed class AddEditNoteEvents {
    data class TitleChange(val text : String) : AddEditNoteEvents()
    data class TitleFocusChange(val focusState : FocusState) : AddEditNoteEvents()
    data class DescriptionChange(val text : String) : AddEditNoteEvents()
    data class DescriptionFocusChange(val focusState : FocusState) : AddEditNoteEvents()
    data class ColorChange(val noteColor: TaskColor ) : AddEditNoteEvents()
    data class DeleteNote(val note: Note ) : AddEditNoteEvents()
    object InsertNote : AddEditNoteEvents()

}
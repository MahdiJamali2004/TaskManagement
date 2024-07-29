package com.solo.tasky.feature_note.presentation.noteScreen

import androidx.compose.ui.focus.FocusState
import com.solo.tasky.feature_note.domain.model.Note

sealed class NoteEvent {
    data class NoteTypeChange(val noteType: String) : NoteEvent()
    data class SearchQueryChange(val text: String) : NoteEvent()
    data class SearchFocusChange(val focusState: FocusState) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
}
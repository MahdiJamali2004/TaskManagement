package com.solo.tasky.feature_note.presentation.noteScreen

import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_note.domain.model.NoteType
import com.solo.tasky.feature_task.presentation.taskHome.SearchBarState

data class NoteScreenStates(
    val notes :List<Note> = emptyList(),
    val noteType: String = NoteType.ALL,
    val searchBarState: SearchBarState = SearchBarState(),
)

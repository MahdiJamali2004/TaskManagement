package com.solo.tasky.feature_note.presentation.noteScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_note.presentation.noteScreen.NoteEvent
import com.solo.tasky.feature_note.presentation.noteScreen.NoteViewModel
import com.solo.tasky.feature_task.presentation.util.Screen

@Composable
fun NoteScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    receivedNoteType : String?,
    noteViewModel: NoteViewModel = hiltViewModel(),
) {
    LaunchedEffect(receivedNoteType) {
        if (receivedNoteType != null)
            noteViewModel.onEvents(NoteEvent.NoteTypeChange(receivedNoteType))
    }

    NoteScreen(
        modifier = modifier,
        noteScreenState = noteViewModel.noteScreenStates.collectAsState().value,
        onNoteTypeChange = {
            noteViewModel.onEvents(NoteEvent.NoteTypeChange(it))
        },
        onNoteClick = { id, color ->

            navController.navigate(Screen.NoteAddEditScreen.route + "?noteId=$id&noteColor=${color.color.toArgb()}&noteType=${noteViewModel.noteScreenStates.value.noteType}")
        },
        onAddNote = {
            navController.navigate(Screen.NoteAddEditScreen.route + "?noteType=${noteViewModel.noteScreenStates.value.noteType}")

        },
        onSearchChange = {
            noteViewModel.onEvents(NoteEvent.SearchQueryChange(it))

        },
        onDeleteNote = {
            noteViewModel.onEvents(NoteEvent.DeleteNote(it))
        },
        onSearchFocusChange = {
            noteViewModel.onEvents(NoteEvent.SearchFocusChange(it))

        }
    )

}
package com.solo.tasky.feature_note.presentation.addEditNote.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_note.presentation.addEditNote.AddEditNoteEvents
import com.solo.tasky.feature_note.presentation.addEditNote.AddEditNoteViewModel

@Composable
fun RootAddEditNoteScreen(
    modifier: Modifier = Modifier,
    noteColor: Int,
    navController: NavController,
    addEditNoteViewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(true) {
        addEditNoteViewModel.noteException.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

        }
    }

    AddEditNoteScreen(
        modifier = modifier,
        title = addEditNoteViewModel.title.collectAsState().value,
        description = addEditNoteViewModel.description.collectAsState().value,
        noteColor = noteColor,
        noteColorViewModel = addEditNoteViewModel.addEditNoteState.collectAsState().value.noteColor,
        onInsertNote = {
            addEditNoteViewModel.onEvents(AddEditNoteEvents.InsertNote)
            navController.previousBackStackEntry?.savedStateHandle?.set("noteType",addEditNoteViewModel.addEditNoteState.value.noteType)
            navController.navigateUp()
        },
        onTitleChange = {
            addEditNoteViewModel.onEvents(AddEditNoteEvents.TitleChange(it))
        },
        onTitleFocusChange = {
            addEditNoteViewModel.onEvents(AddEditNoteEvents.TitleFocusChange(it))
        },
        onChangeColor = {
            addEditNoteViewModel.onEvents(AddEditNoteEvents.ColorChange(it))
        },
        onDescriptionChange = {
            addEditNoteViewModel.onEvents(AddEditNoteEvents.DescriptionChange(it))

        }
    ) {
        addEditNoteViewModel.onEvents(AddEditNoteEvents.DescriptionFocusChange(it))

    }
}
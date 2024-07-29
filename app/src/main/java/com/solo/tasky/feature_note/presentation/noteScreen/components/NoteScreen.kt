package com.solo.tasky.feature_note.presentation.noteScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_note.domain.model.noteTypes
import com.solo.tasky.feature_note.presentation.noteScreen.NoteScreenStates
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.CustomFilterChip
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.IsScrollingUp
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.TransparentHintTextField
import com.solo.tasky.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteScreenState: NoteScreenStates,
    onDeleteNote : (Note) -> Unit,
    onNoteTypeChange: (String) -> Unit,
    onNoteClick: (id: Int,taskColor: TaskColor) -> Unit,
    onAddNote: () -> Unit,
    onSearchChange: (String) -> Unit,
    onSearchFocusChange: (FocusState) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = lazyListState.IsScrollingUp() ,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                ExtendedFloatingActionButton(onClick = onAddNote) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Add Note",
                        tint = MaterialTheme.colorScheme.secondary

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Add note", color = MaterialTheme.colorScheme.secondary)



                }
            }
        }
    )

    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
                .padding(it)
        ) {

            FlowRow(
                modifier = Modifier
            ) {
                noteTypes.forEach { noteType ->

                    CustomFilterChip(
                        isSelected = noteScreenState.noteType == noteType,
                        onClick = {
                            onNoteTypeChange(noteType)
                        },
                        chipColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        onSelectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        text = noteType
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //SearchBar
            Box() {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransparentHintTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        text = noteScreenState.searchBarState.query,
                        hint = "Search note ...",
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                        hintColor = Color.Gray,
                        isHintVisible = (!noteScreenState.searchBarState.isFocus && noteScreenState.searchBarState.query.isBlank()),
                        onValueChange = onSearchChange,
                        onFocusChange = onSearchFocusChange
                    )

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = White

                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                state = lazyListState
            ) {
                items(noteScreenState.notes) {note ->
                    NoteCard(
                        note = note,
                        onDeleteNote = onDeleteNote,
                        onNoteClick = onNoteClick
                    )
                }
            }

        }
    }

}

//
//@Preview
//@Composable
//fun ShowNoteScreen() {
//
//    NoteScreen(
//        noteScreenState = NoteScreenStates(),
//        onNoteTypeChange = {},
//        onNoteClick = {},
//        onAddNote = { /*TODO*/ },
//        onSearchChange = {},
//        onSearchFocusChange = {}
//    )
//}
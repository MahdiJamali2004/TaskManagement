package com.solo.tasky.feature_note.presentation.addEditNote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_note.domain.model.NoteType
import com.solo.tasky.feature_note.domain.repository.NoteRepository
import com.solo.tasky.feature_task.domain.models.InvalidTaskException
import com.solo.tasky.feature_task.presentation.taskEditAdd.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _title = MutableStateFlow(EditTextState())
    val title = _title.asStateFlow()

    private var _description = MutableStateFlow(EditTextState(hint = "Enter Text ..."))
    val description = _description.asStateFlow()

    private var _addEditNoteState = MutableStateFlow(AddEditNoteStates())
    val addEditNoteState = _addEditNoteState.asStateFlow()

    private var _noteException = MutableSharedFlow<String>()
    val noteException = _noteException.asSharedFlow()


    private var noteId = savedStateHandle.getStateFlow("noteId", -1)
    private var noteType = savedStateHandle.getStateFlow("noteType", NoteType.STUDY)


    init {
//        noteId = savedStateHandle.getStateFlow("noteId", -1)


        viewModelScope.launch(Dispatchers.IO) {
            launch {
                noteType.collectLatest {
                    _addEditNoteState.value = addEditNoteState.value.copy(
                        noteType = noteType.value
                    )
                    Log.v("noteTypeTest", noteType.value)
                    Log.v("noteTypeTest", noteId.value.toString())


                }
            }

            launch {

                noteId.collectLatest {

                    if (noteId.value != -1) {

                        val note = noteRepository.getNoteById(noteId.value)
//                    _noteColor.value = note.color
                        _description.value = description.value.copy(
                            text = note.description
                        )
                        _title.value = title.value.copy(
                            text = note.title
                        )

                        _addEditNoteState.value = addEditNoteState.value.copy(
                            noteType = note.noteType,
                            noteColor = note.noteColor

                        )
                    }

                }
            }
        }

    }

    fun onEvents(addEditNoteEvents: AddEditNoteEvents) {
        when (addEditNoteEvents) {

            is AddEditNoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteRepository.deleteNote(addEditNoteEvents.note)
                }
            }

            is AddEditNoteEvents.ColorChange -> {
                _addEditNoteState.value = addEditNoteState.value.copy(
                    noteColor = addEditNoteEvents.noteColor
                )

            }

            is AddEditNoteEvents.DescriptionChange -> {
                _description.value = description.value.copy(
                    text = addEditNoteEvents.text
                )
            }

            is AddEditNoteEvents.DescriptionFocusChange -> {
                _description.value = description.value.copy(
                    isHintVisible = !addEditNoteEvents.focusState.isFocused && description.value.text.isBlank()
                )
            }

            is AddEditNoteEvents.TitleChange -> {
                _title.value = title.value.copy(
                    text = addEditNoteEvents.text
                )
            }

            is AddEditNoteEvents.TitleFocusChange -> {
                _title.value = title.value.copy(
                    isHintVisible = !addEditNoteEvents.focusState.isFocused && title.value.text.isBlank()
                )
            }

            AddEditNoteEvents.InsertNote -> {
                viewModelScope.launch {
                    try {
                        if (title.value.text.isBlank() && description.value.text.isBlank())
                            throw InvalidTaskException(message = "Note must has title or description.")
                        else {

                            noteRepository.upsertNote(
                                Note(
                                    id = if (noteId.value == -1) null else noteId.value,
                                    title = title.value.text,
                                    description = description.value.text,
                                    noteColor = addEditNoteState.value.noteColor,
                                    noteType = addEditNoteState.value.noteType
                                )
                            )
                        }

                    } catch (e: InvalidTaskException) {
                        _noteException.emit(e.message ?: "Unknown error.")
                    }
                }
            }

        }
    }

}
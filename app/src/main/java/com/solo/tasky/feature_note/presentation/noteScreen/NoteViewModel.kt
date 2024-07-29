package com.solo.tasky.feature_note.presentation.noteScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_note.domain.model.NoteType
import com.solo.tasky.feature_note.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _noteScreenStates = MutableStateFlow(NoteScreenStates())
    val noteScreenStates = _noteScreenStates.asStateFlow()

    init {

        noteRepository.getAllNotes()
            .onEach {
                if (noteScreenStates.value.noteType == NoteType.ALL)
                    _noteScreenStates.value = noteScreenStates.value.copy(notes = it)
                else {
                    _noteScreenStates.value = noteScreenStates.value.copy(
                        notes = it.filter { note ->
                            note.noteType == noteScreenStates.value.noteType
                        }
                    )

                }
            }
            .launchIn(viewModelScope)
    }


    fun onEvents(noteEvent: NoteEvent) {
        when (noteEvent) {
            is NoteEvent.NoteTypeChange -> {
                _noteScreenStates.value = noteScreenStates.value.copy(
                    noteType = noteEvent.noteType
                )

                if (noteScreenStates.value.noteType == NoteType.ALL) {
                    noteRepository.getAllNotes().map {
                        _noteScreenStates.value = noteScreenStates.value.copy(
                            notes = it
                        )
                    }.launchIn(viewModelScope)
                } else {
                    noteRepository.getNotesWithNoteType(noteEvent.noteType)
                        .onEach {

                            _noteScreenStates.value = noteScreenStates.value.copy(

                                notes = it
                            )
                        }.launchIn(viewModelScope)

                }


            }

            is NoteEvent.SearchFocusChange -> {
                _noteScreenStates.value = noteScreenStates.value.copy(
                    searchBarState = _noteScreenStates.value.searchBarState.copy(isFocus = noteEvent.focusState.isFocused)
                )
            }

            is NoteEvent.SearchQueryChange -> {
                _noteScreenStates.value = noteScreenStates.value.copy(
                    searchBarState = _noteScreenStates.value.searchBarState.copy(query = noteEvent.text)
                )

                if (noteScreenStates.value.noteType == NoteType.ALL) {
                    noteRepository.getNotesByTitle(noteScreenStates.value.searchBarState.query)
                        .onEach {
                            _noteScreenStates.value = noteScreenStates.value.copy(
                                notes = it
                            )
                        }.launchIn(viewModelScope)
                } else {
                    noteRepository.getNotesByNoteTypeNoteTitle(
                        noteScreenStates.value.noteType,
                        noteScreenStates.value.searchBarState.query
                    ).onEach {
                        _noteScreenStates.value = noteScreenStates.value.copy(
                            notes = it
                        )
                    }.launchIn(viewModelScope)
                }

            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {

                    noteRepository.deleteNote(noteEvent.note)

                }
            }
        }
    }



}
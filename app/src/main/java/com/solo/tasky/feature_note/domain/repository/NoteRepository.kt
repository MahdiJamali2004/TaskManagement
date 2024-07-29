package com.solo.tasky.feature_note.domain.repository

import com.solo.tasky.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Int): Note
    fun getNotesWithNoteType(noteType: String): Flow<List<Note>>
    fun getNotesByNoteTypeNoteTitle(noteType: String , noteTitle :String): Flow<List<Note>>
    fun getNotesByTitle(title: String): Flow<List<Note>>
    suspend fun deleteNote(note: Note)
    suspend fun upsertNote(note: Note)

}
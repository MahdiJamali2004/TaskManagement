package com.solo.tasky.feature_note.data.repository

import com.solo.tasky.feature_note.data.local.NoteDao
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Int): Note {
        return noteDao.getNoteById(id)
    }

    override fun getNotesWithNoteType(noteType: String): Flow<List<Note>> {
        return noteDao.getNotesWithNoteType(noteType)
    }

    override fun getNotesByNoteTypeNoteTitle(
        noteType: String,
        noteTitle: String
    ): Flow<List<Note>> {

        return noteDao.getNotesByNoteTypeNoteTitle(noteType, noteTitle)
    }

    override fun getNotesByTitle(title: String): Flow<List<Note>> {
        return noteDao.getNotesByTitle(title)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun upsertNote(note: Note) {
        noteDao.upsertNote(note)
    }
}
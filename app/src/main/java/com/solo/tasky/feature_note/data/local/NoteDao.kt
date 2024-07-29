package com.solo.tasky.feature_note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.solo.tasky.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: Int): Note

    @Query("SELECT * FROM Note WHERE noteType = :noteType")
    fun getNotesWithNoteType(noteType: String): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :title || '%' ")
    fun getNotesByTitle(title: String): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :title || '%' AND noteType = :noteType")
    fun getNotesByNoteTypeNoteTitle(noteType : String,title: String): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Upsert
    suspend fun upsertNote(note: Note)


}
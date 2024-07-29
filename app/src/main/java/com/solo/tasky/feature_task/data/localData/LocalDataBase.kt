package com.solo.tasky.feature_task.data.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solo.tasky.feature_note.data.local.NoteDao
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_task.domain.models.Converters
import com.solo.tasky.feature_task.domain.models.Task

@TypeConverters(Converters::class)
@Database(
    entities = [Task::class , Note::class]  ,
    version = 1 ,
    exportSchema = false
)
abstract class LocalDataBase :RoomDatabase() {

    abstract val taskDao : TaskDao
    abstract val noteDao : NoteDao

    companion object {
        const val DATA_BASE = "task.db"
    }
}
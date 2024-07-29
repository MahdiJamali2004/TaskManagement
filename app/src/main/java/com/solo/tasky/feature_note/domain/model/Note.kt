package com.solo.tasky.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.solo.tasky.feature_task.domain.models.Converters
import com.solo.tasky.feature_task.domain.models.TaskColor

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,
    val title : String,
    @TypeConverters(Converters::class)
    val noteColor : TaskColor,
    val description : String,
    @TypeConverters(Converters::class)
    val noteType: String
)

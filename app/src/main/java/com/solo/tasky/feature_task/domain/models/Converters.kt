package com.solo.tasky.feature_task.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.solo.tasky.feature_note.domain.model.NoteType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    @TypeConverter
    fun fromString(value: String?): MutableList<SubTask>? {
        if (value == null) return null
        val listType = object : TypeToken<MutableList<SubTask>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<SubTask>?): String? {
        return Gson().toJson(list)
    }


    @TypeConverter
    fun fromString2(value: String?): TaskColor? {
        if (value == null) return null
        val listType = object : TypeToken<TaskColor>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(taskColor: TaskColor?): String? {
        return Gson().toJson(taskColor)
    }


    @TypeConverter
    fun fromString3(value: String?): NoteType? {
        if (value == null) return null
        val listType = object : TypeToken<NoteType>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(noteType: NoteType?): String? {
        return Gson().toJson(noteType)
    }


    @TypeConverter
    fun jsonToColor(value: String?): List<Color>? {
        if (value == null) return null
        val listType = object : TypeToken<List<Color>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun colorToJson(colors: List<Color>?): String? {
        return Gson().toJson(colors)
    }

}
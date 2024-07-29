package com.solo.tasky.feature_task.domain.repository

import com.solo.tasky.feature_task.domain.models.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.Month

interface TaskRepository {

    suspend fun deleteTask(task: Task)

    suspend fun insertTask(task: Task)

    fun getAllTaskByDate(date: String): Flow<List<Task>>

     fun getTaskByTitle(title: String): Flow<List<Task>>

     suspend fun getTaskById(id: Int): Task

    fun getDaysOfMonth(year: Int, month: Month): Flow<List<LocalDate>>

    fun getAllTasks() : Flow<List<Task>>

}
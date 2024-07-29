package com.solo.tasky.feature_task.data.repository

import com.solo.tasky.feature_task.data.localData.TaskDao
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun getAllTaskByDate(date: String): Flow<List<Task>> {
        return taskDao.getAllTasksByDate(date)

    }

    override fun getTaskByTitle(title: String): Flow<List<Task>> {
        return taskDao.getTaskByTitle(title)
    }

    override suspend fun getTaskById(id: Int): Task {
        return taskDao.getTaskById(id)
    }

    override fun getDaysOfMonth(year: Int, month: Month): Flow<List<LocalDate>> {
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())
        val list = (1..lastDayOfMonth.dayOfMonth).map { firstDayOfMonth.withDayOfMonth(it) }
        return flowOf(list)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

}
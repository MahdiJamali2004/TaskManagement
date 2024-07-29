package com.solo.tasky.feature_task.domain.usecases

import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend fun autoDeleteTask()  {
        val oneMonthInMillis = 30L * 24 * 60 * 60 * 1000 // Approximately 30 days
        val currentTimestamp = System.currentTimeMillis()

        taskRepository.getAllTasks().onEach {
            it.onEach {
                if ( currentTimestamp - it.timestamp >= oneMonthInMillis) {
                    taskRepository.deleteTask(it)
                }
            }
        }


    }

    suspend fun deleteTask(task: Task) {
        taskRepository.deleteTask(task)
    }
}
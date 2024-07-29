package com.solo.tasky.feature_task.domain.usecases

import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.repository.TaskRepository
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    fun getTasksSortedBy(taskOrder: TaskOrder, date: String): Flow<List<Task>> {
        taskRepository.getAllTaskByDate(date).let { tasks ->

            return when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is TaskOrder.ByNumOfSubTasks -> {
                            tasks.map { it.sortedBy { task -> task.subTasks.size } }
                        }

                        is TaskOrder.ByPriority -> {
                            tasks.map {
                                it.sortedBy { task ->
                                    when (task.priority) {
                                        "High" -> 3
                                        "Medium" -> 2
                                        "Low" -> 1
                                        else ->0
                                    }
                                }
                            }
                        }

                        is TaskOrder.ByTitle -> {
                            tasks.map { it.sortedBy { task -> task.title.lowercase() } }
                        }

                        is TaskOrder.ByTimeAdded -> {
                            tasks.map { it.sortedBy { task -> task.timestamp } }
                        }
                    }
                }

                is OrderType.Descending -> {
                    when (taskOrder) {

                        is TaskOrder.ByNumOfSubTasks -> {
                            tasks.map { it.sortedByDescending { task -> task.subTasks.size } }
                        }

                        is TaskOrder.ByPriority -> {
                            tasks.map {
                                it.sortedByDescending { task ->
                                    when (task.priority) {
                                        "High" -> 3
                                        "Medium" -> 2
                                        "Low" -> 1
                                        else ->0
                                    }

                                }
                            }
                        }

                        is TaskOrder.ByTitle -> {
                            tasks.map { it.sortedByDescending { task -> task.title.lowercase() } }
                        }

                        is TaskOrder.ByTimeAdded -> {
                            tasks.map { it.sortedByDescending { task -> task.timestamp } }
                        }
                    }

                }


            }
        }

    }

    fun getTaskByTitle(title: String): Flow<List<Task>> {
        return taskRepository.getTaskByTitle(title)
    }

    suspend fun getTaskById(id: Int): Task {
        return taskRepository.getTaskById(id)
    }

    fun getDaysOfMonth(localDate: LocalDate): Flow<List<LocalDate>> {
        return taskRepository.getDaysOfMonth(localDate.year, localDate.month)
    }
    fun getAllTasks(): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }


}
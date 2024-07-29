package com.solo.tasky.feature_task.domain.usecases

import com.solo.tasky.feature_task.domain.models.InvalidTaskException
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.repository.TaskRepository
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    @Throws(InvalidTaskException::class)
    suspend fun insertTask(task: Task) {

         if (task.title.isBlank() && task.description.isBlank())
             throw InvalidTaskException("task must has title or description.")
        else
            taskRepository.insertTask(task)

    }

}
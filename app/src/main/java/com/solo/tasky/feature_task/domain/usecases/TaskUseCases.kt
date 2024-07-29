package com.solo.tasky.feature_task.domain.usecases

data class TaskUseCases (
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getTasksUseCase: GetTasksUseCase ,
    val insertTaskUseCase: InsertTaskUseCase,
)

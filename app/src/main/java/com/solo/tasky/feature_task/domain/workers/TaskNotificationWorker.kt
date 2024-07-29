package com.solo.tasky.feature_task.domain.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.task_notifications.TaskNotification
import com.google.gson.Gson

class TaskNotificationWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {

        val task = Gson().fromJson(workerParameters.inputData.getString(TASK_KEY),Task::class.java)
        TaskNotification(context).showNotification(
            task
        )
        return Result.success()
    }

    companion object {
        const val TASK_KEY = "task"
    }


}
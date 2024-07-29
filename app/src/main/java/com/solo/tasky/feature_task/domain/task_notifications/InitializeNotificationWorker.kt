package com.solo.tasky.feature_task.domain.task_notifications

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.solo.tasky.feature_task.data.util.Constants
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.usecases.TaskUseCases
import com.solo.tasky.feature_task.domain.workers.TaskNotificationWorker
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class InitializeNotificationWorker @Inject constructor(
    private val context: Context
) {
    private lateinit var workManager: WorkManager

    suspend fun setTaskNotification(task: Task) {
        workManager = WorkManager.getInstance(context)


        val format = DateTimeFormatter.ofPattern("HH:mm a")
        withContext(Dispatchers.IO) {
            val taskTime = LocalDateTime.of(
                LocalDate.parse(
                    task.dateOfTask,
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
                ), LocalTime.parse(task.startTime, format)
            )
            Log.v("testtest", "$taskTime")

            val differenceTime = Duration.between(LocalDateTime.now(), taskTime)
            Log.v("testtest", "min :${differenceTime.toMinutes()} , hour :${differenceTime.toHours()}  ")

            if (differenceTime.seconds > 0) {

                val decodeTask = Gson().toJson(task)
                val work = OneTimeWorkRequest.Builder(TaskNotificationWorker::class.java)
                    .setInputData(
                        workDataOf(
                            TaskNotificationWorker.TASK_KEY to decodeTask
                        )
                    )
                    .addTag(task.hashCode().toString())
                    .setInitialDelay(differenceTime)
                    .build()

                workManager.enqueue(work)
            }

        }
    }


    suspend fun cancelTaskNotification(
        task: Task
    ) {
        workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(task.hashCode().toString())
    }


}

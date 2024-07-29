package com.solo.tasky.feature_task.domain.task_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class TaskNotificationChannel(
    private val context: Context
) {

    fun createTaskNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(
                notificationChannelId,
                "task_notification",
                NotificationManager.IMPORTANCE_HIGH
            )
        )


    }

    companion object {
        val notificationChannelId = "tasK_notification"
    }
}
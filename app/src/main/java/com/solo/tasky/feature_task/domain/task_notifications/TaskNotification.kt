package com.solo.tasky.feature_task.domain.task_notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.solo.tasky.MainActivity
import com.solo.tasky.R
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.ui.theme.Black

class TaskNotification(private val context: Context)  {
    fun showNotification(task : Task) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            task.hashCode(),
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context,TaskNotificationChannel.notificationChannelId)
            .setContentTitle("Task ${task.title} begin.")
            .setContentText(task.description)
            .setSmallIcon(R.drawable.ic_task)
            .setContentIntent(contentIntent)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setColor(Black.toArgb())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1,notification)

    }
}
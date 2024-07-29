package com.solo.tasky

import android.app.Application
import com.solo.tasky.feature_task.domain.task_notifications.TaskNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        TaskNotificationChannel(applicationContext).createTaskNotification()

    }
}
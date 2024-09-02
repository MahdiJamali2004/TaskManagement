package com.solo.tasky.di

import android.app.Application
import androidx.room.Room
import com.solo.tasky.feature_login.data.LoginRepositoryImpl
import com.solo.tasky.feature_login.data.LoginSharedPreferences
import com.solo.tasky.feature_login.domain.login_use_cases.InsertGmailUseCase
import com.solo.tasky.feature_login.domain.login_use_cases.InsertUserNameUseCase
import com.solo.tasky.feature_login.domain.repository.LoginRepository
import com.solo.tasky.feature_note.data.repository.NoteRepositoryImpl
import com.solo.tasky.feature_note.domain.repository.NoteRepository
import com.solo.tasky.feature_task.data.localData.LocalDataBase
import com.solo.tasky.feature_task.data.repository.TaskRepositoryImpl
import com.solo.tasky.feature_task.domain.repository.TaskRepository
import com.solo.tasky.feature_task.domain.task_notifications.InitializeNotificationWorker
import com.solo.tasky.feature_task.domain.usecases.DeleteTaskUseCase
import com.solo.tasky.feature_task.domain.usecases.GetTasksUseCase
import com.solo.tasky.feature_task.domain.usecases.InsertTaskUseCase
import com.solo.tasky.feature_task.domain.usecases.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    fun provideTaskDao(app: Application): LocalDataBase {
        return Room.databaseBuilder(
            app.applicationContext,
            LocalDataBase::class.java,
            LocalDataBase.DATA_BASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideTaskRepository(database: LocalDataBase): TaskRepository {
        return TaskRepositoryImpl(database.taskDao)
    }

    @Singleton
    @Provides
    fun provideTaskUseCase(taskRepository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            deleteTaskUseCase = DeleteTaskUseCase(taskRepository),
            getTasksUseCase = GetTasksUseCase(taskRepository),
            insertTaskUseCase = InsertTaskUseCase(taskRepository)
        )
    }

    @Singleton
    @Provides
    fun provideGetTaskUseCase(taskRepository: TaskRepository): GetTasksUseCase {
        return GetTasksUseCase(taskRepository)
    }


    @Provides
    @Singleton
    fun providesInsertGmailUseCase(): InsertGmailUseCase {
        return InsertGmailUseCase()
    }

    @Provides
    @Singleton
    fun providesInsertUserNameUseCase(): InsertUserNameUseCase {
        return InsertUserNameUseCase()
    }

    @Provides
    @Singleton
    fun provideLoginSharedPreferences(app: Application): LoginSharedPreferences {
        return LoginSharedPreferences(app)
    }

    @Provides
    @Singleton
    fun providesLoginRepository(loginSharedPreferences: LoginSharedPreferences): LoginRepository {
        return LoginRepositoryImpl(loginSharedPreferences)
    }

    @Provides
    @Singleton
    fun providesNoteRepository(database: LocalDataBase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideInitializeNotificationWorker(app: Application ): InitializeNotificationWorker {
        return InitializeNotificationWorker(app.applicationContext)
    }

}
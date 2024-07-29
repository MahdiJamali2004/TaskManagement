package com.solo.tasky.feature_task.data.localData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.solo.tasky.feature_task.domain.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun insertTask(task : Task)

    @Delete
    suspend fun deleteTask(task: Task)
// date should be like = "MMM, yyyy EEE dd"(Jan, 2024 Mon 22)
    @Query("SELECT * FROM Task WHERE dateOfTask = :date")
     fun getAllTasksByDate(date: String) :Flow<List<Task>>

     @Query("SELECT * FROM Task WHERE title LIKE :title || '%'")
      fun getTaskByTitle(title : String) : Flow<List<Task>>

      @Query("SELECT * FROM Task WHERE id = :id")
      suspend fun getTaskById (id : Int) : Task

     @Query("SELECT * FROM Task")
     fun getAllTasks() : Flow<List<Task>>

}
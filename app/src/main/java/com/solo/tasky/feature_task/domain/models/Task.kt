package com.solo.tasky.feature_task.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.solo.tasky.ui.theme.BabyBlue
import com.solo.tasky.ui.theme.BabyBlueLight
import com.solo.tasky.ui.theme.BabyBlueText
import com.solo.tasky.ui.theme.Blue
import com.solo.tasky.ui.theme.BlueLight
import com.solo.tasky.ui.theme.BlueText
import com.solo.tasky.ui.theme.Green
import com.solo.tasky.ui.theme.GreenLight
import com.solo.tasky.ui.theme.GreenText
import com.solo.tasky.ui.theme.Purple
import com.solo.tasky.ui.theme.PurpleLight
import com.solo.tasky.ui.theme.PurpleText
import com.solo.tasky.ui.theme.RedOrange
import com.solo.tasky.ui.theme.RedOrangeLight
import com.solo.tasky.ui.theme.RedOrangeText
import com.solo.tasky.ui.theme.RedPink
import com.solo.tasky.ui.theme.RedPinkLight
import com.solo.tasky.ui.theme.RedPinkText
import com.solo.tasky.ui.theme.Violet
import com.solo.tasky.ui.theme.VioletLight
import com.solo.tasky.ui.theme.VioletText

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val startTime: String,
    @TypeConverters(Converters::class)
    val taskColor: TaskColor,
    @TypeConverters(Converters::class)
    val subTasks: MutableList<SubTask>,
    val timestamp: Long,
    val dateOfTask: String,
    @TypeConverters(Converters::class)
    val priority: String,
    val isDone: Boolean
) {
    companion object {
        val taskColors = listOf<TaskColor>(
            TaskColor(color = Green, lightColor = GreenLight, textColor = GreenText),
            TaskColor(color = Blue, lightColor = BlueLight, textColor = BlueText),
            TaskColor(color = Purple, lightColor = PurpleLight, textColor = PurpleText),
            TaskColor(color = RedPink, lightColor = RedPinkLight, textColor = RedPinkText),
            TaskColor(color = RedOrange, lightColor = RedOrangeLight, textColor = RedOrangeText),
            TaskColor(color = BabyBlue, lightColor = BabyBlueLight, textColor = BabyBlueText),
            TaskColor(color = Violet, lightColor = VioletLight, textColor = VioletText)
        )
    }
}

data class SubTask(
    val title: String,
    val isDone: Boolean

)

data class TaskColor(
    val color: Color,
    val lightColor: Color,
    val textColor: Color
)

class InvalidTaskException(message: String) : Exception(message)


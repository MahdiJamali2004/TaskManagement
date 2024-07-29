package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.ui.theme.White

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    isSelected : Boolean,
    taskColor: TaskColor,
    onClick : () -> Unit
) {

    Box(modifier = modifier
        .size(if (isSelected) 60.dp else 50.dp)
        .shadow(16.dp, shape = CircleShape)
        .background(color = taskColor.color, shape = CircleShape)
        .clip(CircleShape)
        .border(width = 3.dp, color = if (isSelected) White else taskColor.color, shape = CircleShape)
        .clickable {
            onClick()
        }
    )
}
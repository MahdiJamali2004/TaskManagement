package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solo.tasky.feature_task.domain.models.TaskColor

@Composable
fun ColorListSection(
    modifier: Modifier = Modifier,
    colorList : List<TaskColor>,
    currentTaskColor: TaskColor,
    onColorItemClick : (TaskColor) -> Unit
) {


        LazyRow(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = 16.dp , vertical = 8.dp)
        ) {
            items(colorList) {
                ColorItem(
                    isSelected = currentTaskColor == it,
                    taskColor = it ,
                    onClick = {
                        onColorItemClick(it)
                    }
                )

            }
        }

}
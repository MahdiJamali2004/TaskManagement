package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.solo.tasky.feature_task.presentation.taskEditAdd.SubTaskState
import com.solo.tasky.ui.theme.GreenDone
import com.solo.tasky.ui.theme.LightText

@Composable
fun SubTaskItem(
    modifier: Modifier = Modifier,
    subTaskState: SubTaskState,
    onClick: () -> Unit,
    onValueChange: (text: String) -> Unit,
    onFocusStateChange: (focusState: FocusState) -> Unit,
    onDeleteSubTask : () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (subTaskState.isDone) GreenDone else Color.Transparent,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (subTaskState.isDone) GreenDone else MaterialTheme.colorScheme.onPrimary,
                    CircleShape
                )
                .padding(4.dp),
            imageVector = Icons.Default.Done,
            contentDescription = "Done",
            tint = if (subTaskState.isDone) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onPrimary
        )



        Spacer(modifier = Modifier.width(16.dp))

        TransparentHintTextField(
            modifier = Modifier.weight(1f),
            text = subTaskState.text,
            hint = subTaskState.hint,
            isHintVisible = subTaskState.isHintVisible,
            hintColor = LightText,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                textDecoration = if (subTaskState.isDone) TextDecoration.LineThrough else TextDecoration.None
            ),
            onValueChange = onValueChange,
            onFocusChange = onFocusStateChange
        )

        Spacer(modifier = Modifier.width(16.dp))


        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                onDeleteSubTask()
            },
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete subTask"
        )


    }

}
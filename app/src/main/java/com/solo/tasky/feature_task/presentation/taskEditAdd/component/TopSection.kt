package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.R
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.feature_task.presentation.taskEditAdd.EditTextState
import com.solo.tasky.ui.theme.GreenDone


import com.solo.tasky.ui.theme.White


@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit,
    onStartTimeClick: () -> Unit,
    onDateClick: () -> Unit,
    onFocusTitleChange: (FocusState) -> Unit,
    onTitleChange: (title: String) -> Unit,
    onPriorityItemClick: (priority: String) -> Unit,
    editTextTitle: EditTextState,
    startTime: String,
    date: String,
    isDone: Boolean,
    onDoneClick: () -> Unit,
    taskColor: TaskColor,
    taskPriority: String
) {
    BoxWithConstraints(
        modifier = modifier
            .height(350.dp)
            .background(
                taskColor.color,
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
            )
    ) {


        Canvas(modifier = Modifier.matchParentSize()) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()
            val path = Path().apply {
                val offset1 = Offset(width * 0.9f, 0f)
                val offset2 = Offset(width * 0.65f, height * 0.8f)
                val offset3 = Offset(width * 0.6f, height)
                val offset4 = Offset(width * 0.8f, height)
                val offset5 = Offset(width, height * 0.2f)

                moveTo(offset1.x, offset1.y)
                quadraticBezierTo(width * 0.65f, height * 0.2f, offset2.x, offset2.y)
                quadraticBezierTo(width * 0.65f, height * 1, offset3.x, offset3.y)
                lineTo(offset4.x, offset4.y)
                quadraticBezierTo(width * 0.7f, height * 0.45f, offset5.x, offset5.y)
                lineTo(width, -height)
                close()
            }

            drawPath(path, taskColor.lightColor)

        }

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Icon(
                    modifier = Modifier.clickable {
                        onArrowBackClick()
                    },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = MaterialTheme.colorScheme.onPrimary
                )




                Text(
                    modifier = Modifier,
                    text = "Task Details",
                    color = White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Icon(
                    modifier = Modifier
                        .background(
                            color = if (isDone) GreenDone else Color.Transparent,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            onDoneClick()
                        }
                        .padding(4.dp),
                    imageVector = Icons.Default.Done,
                    contentDescription = "done task",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = "Due Priority",
                color = taskColor.textColor,
                fontSize = 12.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomFilterChip(
                    modifier.padding(end = 16.dp),
                    text = "High",
                    isSelected = taskPriority == "High",
                    onClick = {
                        onPriorityItemClick("High")
                    }
                )
                CustomFilterChip(
                    modifier.padding(end = 16.dp),
                    text = "Medium",
                    isSelected = taskPriority == "Medium",
                    onClick = {
                        onPriorityItemClick("Medium")

                    }
                )
                CustomFilterChip(
                    modifier.padding(end = 16.dp),
                    text = "Low",
                    isSelected = taskPriority == "Low",
                    onClick = {
                        onPriorityItemClick("Low")

                    }
                )


            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Task Title",
                color = taskColor.textColor,
                fontSize = 12.sp
            )

            TransparentHintTextField(
                text = editTextTitle.text,
                hint = editTextTitle.hint,
                hintColor = taskColor.textColor,
                isHintVisible = editTextTitle.isHintVisible,
                onValueChange = onTitleChange,
                onFocusChange = onFocusTitleChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )

            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Due date",
                color = taskColor.textColor,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onStartTimeClick()
                        },
                    painter = painterResource(id = R.drawable.ic_alarm),
                    contentDescription = "SetTime",
                    tint = White
                )


                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp),
                    text = startTime,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onDateClick()
                        },
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "DatePicker",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    text = date.substring(10, 16),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )

            }


        }


    }


}


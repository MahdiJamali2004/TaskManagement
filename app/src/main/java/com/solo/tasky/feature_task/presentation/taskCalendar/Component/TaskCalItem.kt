package com.solo.tasky.feature_task.presentation.taskCalendar.Component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.R
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.ui.theme.LightBlack
import com.solo.tasky.ui.theme.LightText


@Composable
fun TaskCalItem(
    title: String,
    startTime: String,
    date: String,
    isDone: Boolean,
    numSubTask: Int,
    numDoneSubTask: Int,
    taskColor: TaskColor,
    onTaskClick: () -> Unit,
    onDeleteTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = startTime,
            color = LightText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier
                .weight(1f)
                .background(color = LightBlack, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onTaskClick()
                }
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_alarm),
                        contentDescription = null,
                        tint = LightText
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = startTime,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Start,
                        color = LightText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = LightText
                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = date,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Start,
                        color = LightText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        modifier = Modifier.clickable {
                            onDeleteTaskClick()
                        },
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete Task",
                        tint = LightText
                    )


                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "delete task",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Start,
                        color = LightText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }

            Spacer(modifier = Modifier.width(16.dp))

            CircleProgress(
                fullPercentage = numSubTask,
                currentPercentage = numDoneSubTask,
                isDone = isDone,
                taskColor = taskColor

            )


        }

    }

}
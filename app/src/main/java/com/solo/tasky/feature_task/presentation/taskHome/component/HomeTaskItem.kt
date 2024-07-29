package com.solo.tasky.feature_task.presentation.taskHome.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.ui.theme.GreenDone
import com.solo.tasky.ui.theme.ProgressBarColor
import com.solo.tasky.ui.theme.White

@Composable
fun HomeTaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onEditTaskClick: (id: Int) -> Unit
) {


    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(if (task.description.isBlank()) 1f else 2 / 4f)
            .background(
                task.taskColor.color,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {


        Canvas(modifier = Modifier.matchParentSize()) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()
            val path = Path().apply {
                val offset1 = Offset(width, 0.1f * height)
                val offset2 = Offset(width * 0.6f, height * 0.8f)
                val offset3 = Offset(width * 0.4f, height)
                val offset4 = Offset(width * 0.8f, height)
                val offset5 = Offset(width, height * 0.4f)

                moveTo(offset1.x, offset1.y)
                quadraticBezierTo(width * 0.6f, height * 0.2f, offset2.x, offset2.y)
                quadraticBezierTo(width * 0.6f, height * 1f, offset3.x, offset3.y)
                lineTo(offset4.x, offset4.y)
                quadraticBezierTo(width * 0.7f, height * 0.45f, offset5.x, offset5.y)
                lineTo(width, 0f)
                close()
            }

            drawPath(path, task.taskColor.lightColor)

        }

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = ProgressBarColor,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            onEditTaskClick(task.id!!)
                        }
                        .padding(6.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit task",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                if (task.isDone) {

                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = GreenDone,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .padding(4.dp),
                        imageVector = Icons.Default.Done,
                        contentDescription = "done task",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier,
                text = task.title,
                color = White,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            if (task.description.isNotBlank()) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = task.description,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            val progress = if (task.subTasks.isEmpty()) {
                if (task.isDone)
                    100f
                else
                    0f

            } else {
                (task.subTasks.filter { it.isDone }.size * 100 / task.subTasks.size).toFloat()

            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    LinearProgressIndicator(modifier =Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(32.dp)) ,
                        progress = { progress / 100 },
                        strokeCap = StrokeCap.Round,
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = ProgressBarColor
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "progress",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(

                            fontWeight = FontWeight.Bold,
                            text = "${progress.toInt()}%",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            }


        }


    }
}
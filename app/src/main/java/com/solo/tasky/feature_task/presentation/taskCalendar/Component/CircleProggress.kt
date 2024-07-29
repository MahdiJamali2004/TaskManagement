package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.ui.theme.ProgressBarColor

@Composable
fun CircleProgress(
    modifier: Modifier = Modifier,
    fullPercentage: Int,
    currentPercentage: Int,
    isDone: Boolean ,
    fontWeightEnable : Boolean =false,
    percentageSize : Int = 14,
    taskColor: TaskColor,
    progressStroke : Float = 14f
) {

    val progressState =
        if (fullPercentage == 0) {
            if (isDone)
                100
            else
                0
        } else
            ((currentPercentage * 100) / fullPercentage)
    val percentageAnimation by animateIntAsState(
        targetValue = progressState,
        label = "percentageAnimation"
    )
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.size(50.dp)) {
            drawArc(
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                color = ProgressBarColor,
                style = Stroke(width = progressStroke)
            )
            drawArc(
                startAngle = -90f,
                sweepAngle = ((360 * percentageAnimation) / 100).toFloat(),
                useCenter = false,
                color = taskColor.color,
                style = Stroke(width = progressStroke)
            )
        }
        //((currentPercentage * 100) / fullPercentage).toString() + "%"
        Text(
            text = "$percentageAnimation%",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = if (fontWeightEnable) FontWeight.Bold else FontWeight.Normal ,
            fontSize = percentageSize.sp
        )


    }
}
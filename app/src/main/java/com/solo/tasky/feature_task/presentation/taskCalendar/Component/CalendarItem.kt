package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CalendarItem(
    modifier: Modifier = Modifier,
    day: String,
    week: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {


    Column(
        modifier = modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
            .size(width = 60.dp, height = 80.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onPrimary , shape = RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            }
            .padding(6.dp)
            .padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = week,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = day,
            style = MaterialTheme.typography.labelLarge,
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
        )


    }


}

package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.solo.tasky.ui.theme.DarkWhite

@Composable
fun RadioItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onCLick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    //    Spacer(modifier = Modifier.width(4.dp))
        RadioButton(selected = isSelected, onClick = onCLick
        ,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.tertiary, unselectedColor = DarkWhite)
        )

    }

}
package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    timeDialogState: MaterialDialogState,
    onClick: (time: LocalTime) -> Unit
) {

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = MaterialTheme.colorScheme.background , fontWeight = FontWeight.Bold)) {

            }
            negativeButton(text = "Close", textStyle = TextStyle(color = MaterialTheme.colorScheme.background , fontWeight = FontWeight.Bold)) {

            }
        }
    ) {
        timepicker(
            colors = TimePickerDefaults.colors(
                selectorColor = MaterialTheme.colorScheme.secondary
                ),
            initialTime = LocalTime.now(),
            title = "Pick a Time",
        ) {
            onClick(it)

        }

    }

}
package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DatePickerDialog(
     dateDialogState : MaterialDialogState ,
     onClick : (date : LocalDate) -> (Unit)
) {
    var dateState by remember {
        mutableStateOf(LocalDate.now())
    }

    MaterialDialog(
        dialogState = dateDialogState,
        shape = RoundedCornerShape(16.dp),
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = MaterialTheme.colorScheme.background , fontWeight = FontWeight.Bold)) {
                onClick(dateState)
            }


            negativeButton(text = "Close", textStyle = TextStyle(color = MaterialTheme.colorScheme.background , fontWeight = FontWeight.Bold)) {

            }
        }
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.background,
                headerTextColor = MaterialTheme.colorScheme.onPrimary ,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.background,
            ),
            initialDate = LocalDate.now() ,
            title = "Pick a Date",

        ) {

            dateState = it
        }

    }
}
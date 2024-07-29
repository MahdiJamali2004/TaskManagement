package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFilterChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    chipColor: Color = MaterialTheme.colorScheme.secondary,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    onSelectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    text: String
) {

    FilterChip(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(text = text, color = if (isSelected) selectedTextColor else onSelectedTextColor)
        }, colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = chipColor
        ),
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done icon",
                    tint = selectedTextColor
                )
            }
        }
    )

}
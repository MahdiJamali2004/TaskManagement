package com.solo.tasky.feature_login.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (text: String) -> Unit,
    onFocusChange: (focusState: FocusState) -> Unit,
    isError : Boolean,
    label: String,
    focusState: Boolean,
    errorText: String?,
    trailingIconDefault: ImageVector,
    trailingIconField: ImageVector,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardOptions.Default.keyboardType
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                onFocusChange(it)
            },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder, color = Color.Gray)
        },
        trailingIcon = {

            Icon(
                imageVector = if (focusState) trailingIconField else trailingIconDefault,
                contentDescription = null
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        supportingText = {
            if (errorText != null) {

                Text(text = errorText, color = MaterialTheme.colorScheme.error)
            }


        }
    )

}


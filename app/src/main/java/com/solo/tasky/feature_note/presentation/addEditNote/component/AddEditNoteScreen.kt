package com.solo.tasky.feature_note.presentation.addEditNote.component


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.feature_task.presentation.taskEditAdd.EditTextState
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.TransparentHintTextField
import kotlinx.coroutines.launch


@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    noteColor :Int,
    noteColorViewModel: TaskColor,
    title: EditTextState,
    description: EditTextState,
    onChangeColor : (TaskColor) -> Unit,
    onInsertNote: () -> Unit,
    onTitleChange: (String) -> Unit,
    onTitleFocusChange: (FocusState) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDescriptionFocusChange: (FocusState) -> Unit,
) {


    val scope = rememberCoroutineScope()
    val colorAnimation = remember {
        Animatable(if (noteColor != -1) Color(noteColor) else noteColorViewModel.color)
    }


    Scaffold(modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onInsertNote) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "InsertNote",
                    tint = MaterialTheme.colorScheme.secondary
                )

            }
        }
    )

    { paddingValue ->
        Column(
            modifier = Modifier
                .background(colorAnimation.value)
                .fillMaxSize()
                .padding(paddingValue)
        ) {


            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Task.taskColors.forEach { taskColor ->
                    val colorInt = taskColor.color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(if (noteColorViewModel.color == taskColor.color) 56.dp else 50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(taskColor.color)
                            .border(
                                width = 3.dp,
                                color = if (noteColorViewModel.color == taskColor.color) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    colorAnimation.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onChangeColor(taskColor)

                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            TransparentHintTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                text = title.text,
                hint = title.hint,
                isHintVisible = title.isHintVisible,
                singleLine = true,
                hintColor = noteColorViewModel.textColor,
                onValueChange = onTitleChange,
                onFocusChange = onTitleFocusChange
            )
            Spacer(modifier = Modifier.height(12.dp))
            TransparentHintTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                text = description.text,
                hint = description.hint,
                singleLine = false,
                isHintVisible = description.isHintVisible,
                hintColor = noteColorViewModel.textColor,
                onValueChange = onDescriptionChange,
                onFocusChange = onDescriptionFocusChange
            )


        }


    }
}


//@Preview
//@Composable
//fun ShowAddEditNoteScreen(
//
//) {
//
//    AddEditNoteScreen(
//        title = EditTextState(),
//        description = EditTextState(),
//        noteColorViewModel = Color.Red.toArgb(),
//        onInsertNote = { /*TODO*/ },
//        onTitleChange = {},
//        onTitleFocusChange = {},
//        onChangeColor = {},
//        onDescriptionChange = {}
//    ) {}
//
//}
package com.solo.tasky.feature_task.presentation.taskEditAdd.component

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solo.tasky.feature_task.presentation.taskCalendar.Component.DatePickerDialog
import com.solo.tasky.feature_task.presentation.taskEditAdd.AddEditTaskEvents
import com.solo.tasky.feature_task.presentation.taskEditAdd.TaskAddEditViewModel
import com.solo.tasky.ui.theme.Black
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TaskAddEditScreen(
    navController: NavController,
    taskAddEditViewModel: TaskAddEditViewModel = hiltViewModel()

) {
    val titleState = taskAddEditViewModel.title.collectAsState()
    val descriptionState = taskAddEditViewModel.description.collectAsState()
    val taskAddEditState = taskAddEditViewModel.taskEditAddState.collectAsState()
    val subtasks = taskAddEditViewModel.subTasks.collectAsState()
    val currentDate = taskAddEditViewModel.currentDate.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(true) {
        taskAddEditViewModel.taskException.collectLatest { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

        }
    }


    val dateDialogState = rememberMaterialDialogState()
    val startTimeDialogState = rememberMaterialDialogState()
    DatePickerDialog(dateDialogState = dateDialogState, onClick = {
        taskAddEditViewModel.onEvents(AddEditTaskEvents.DateChange(it))

    })
    TimePickerDialog(timeDialogState = startTimeDialogState, onClick = {
        //onStartTimeChange(it)
        taskAddEditViewModel.onEvents(AddEditTaskEvents.StartTimeChange(it))
    })


    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(visible = lazyListState.IsScrollingUp() ,
                enter = fadeIn() ,
                exit = fadeOut()
            ) {
                FloatingActionButton(onClick = {
                    //onInsertTask()
                    taskAddEditViewModel.onEvents(AddEditTaskEvents.InsertTask)
                    navController.navigateUp()
                }) {

                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "SaveTask",
                        tint = MaterialTheme.colorScheme.secondary
                    )

                }
            }

        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .background(Black)
                .fillMaxSize()
                .padding(it) ,
            state = lazyListState
        ) {

            item {

                TopSection(
                    onArrowBackClick = {
                        navController.navigateUp()
                    },
                    onStartTimeClick = {
                        startTimeDialogState.show()
                    },
                    onDateClick = {
                        dateDialogState.show()
                    },
                    onFocusTitleChange = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.TitleFocusChange(it))
                    },
                    onTitleChange = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.TitleChange(it))
                    },
                    onPriorityItemClick = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.PriorityChange(it))
                    },
                    onDoneClick = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.DoneClick(taskAddEditState.value.isDone))
                    },
                    editTextTitle = titleState.value,
                    startTime = taskAddEditState.value.startTime,
                    isDone = taskAddEditState.value.isDone,
                    date = currentDate.value,
                    taskColor = taskAddEditState.value.taskColor,
                    taskPriority = taskAddEditState.value.priority
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Descriptions",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                //just for padding
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Black, RoundedCornerShape(16.dp))
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)

                    ) {
                        TransparentHintTextField(
                            text = descriptionState.value.text,
                            hint = descriptionState.value.hint,
                            isHintVisible = descriptionState.value.isHintVisible,
                            hintColor = MaterialTheme.colorScheme.onPrimary,
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                            onValueChange = {
                                taskAddEditViewModel.onEvents(AddEditTaskEvents.DescriptionChange(it))
                            },
                            onFocusChange = {
                                taskAddEditViewModel.onEvents(
                                    AddEditTaskEvents.DescriptionFocusChange(
                                        it
                                    )
                                )
                            }
                        )

                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    //if there is no subTask it check if task is done is 100 else 0 otherwise it check howMany of subTasks isDone
                    val progressState =
                        if (taskAddEditState.value.isDone)
                            100f
                        else if (subtasks.value.isEmpty()) {
                            0f
                        } else {
                            ((taskAddEditViewModel.getDoneSubTaskSize() * 100) / subtasks.value.size).toFloat()

                        }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(taskAddEditState.value.taskColor.color, CircleShape)
                                .padding(2.dp)
                                .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (progressState == 100f) "Done" else "In progress",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LinearProgressIndicator(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(32.dp)),
                            progress = { progressState / 100 },
                            strokeCap = StrokeCap.Round,
                            color = taskAddEditState.value.taskColor.color
                        )



                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "${progressState.toInt()}%",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    TextButton(onClick = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.AddSubTask)

                    }) {
                        Text(
                            modifier = Modifier,
                            text = "add subTask",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                /*  subtasks.value.forEachIndexed { index, subTask ->

                      SubTaskItem(
                          subTaskState = subTask.value,
                          onClick = {
                              taskAddEditViewModel.onEvents(
                                  AddEditTaskEvents.UpdateSubTask(
                                      subTask.value.text,
                                      !subTask.value.isDone,
                                      index
                                  )
                              )
                          },
                          onDeleteSubTask = {
                              taskAddEditViewModel.onEvents(AddEditTaskEvents.DeleteSubTask(index))
                          },
                          onValueChange = { text ->
                              taskAddEditViewModel.onEvents(
                                  AddEditTaskEvents.UpdateSubTask(
                                      text,
                                      subTask.value.isDone,
                                      index
                                  )
                              )
                          },
                          onFocusStateChange = { focusState ->
                              taskAddEditViewModel.onEvents(
                                  AddEditTaskEvents.FocusStateChangeSubTask(
                                      focusState, index
                                  )
                              )
                          }
                      )
                      Spacer(modifier = Modifier.height(16.dp))
                  }*/

            }

            itemsIndexed(subtasks.value) { index, subTask ->
                SubTaskItem(
                    subTaskState = subTask.value,
                    onClick = {
                        taskAddEditViewModel.onEvents(
                            AddEditTaskEvents.UpdateSubTask(
                                subTask.value.text,
                                !subTask.value.isDone,
                                index
                            )
                        )
                    },
                    onDeleteSubTask = {
                        taskAddEditViewModel.onEvents(AddEditTaskEvents.DeleteSubTask(index))
                    },
                    onValueChange = { text ->
                        taskAddEditViewModel.onEvents(
                            AddEditTaskEvents.UpdateSubTask(
                                text,
                                subTask.value.isDone,
                                index
                            )
                        )
                    },
                    onFocusStateChange = { focusState ->
                        taskAddEditViewModel.onEvents(
                            AddEditTaskEvents.FocusStateChangeSubTask(
                                focusState, index
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }


        }

    }


}

@Composable
fun LazyListState.IsScrollingUp(
): Boolean {

    var previousItemIndex by remember {
        mutableIntStateOf(this.firstVisibleItemIndex)
    }
    var previousScrollOffset by remember { mutableIntStateOf(this.firstVisibleItemScrollOffset) }

    val isScrollingUp by remember {
        derivedStateOf {
            if (previousItemIndex != this.firstVisibleItemIndex) {
                previousItemIndex > this.firstVisibleItemIndex
            } else {
                previousScrollOffset >= this.firstVisibleItemScrollOffset
            }.also {
                previousScrollOffset = this.firstVisibleItemScrollOffset
                previousItemIndex = this.firstVisibleItemIndex
            }

        }
    }
    return isScrollingUp


}
package com.solo.tasky.feature_task.presentation.taskCalendar.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solo.tasky.R
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import com.solo.tasky.feature_task.presentation.util.Screen
import com.solo.tasky.ui.theme.Black
import com.solo.tasky.ui.theme.LightBlack
import com.solo.tasky.ui.theme.White
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

//Experimental for bottomSheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCalScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onWeekDayItemClick: (day: String) -> Unit,
    onDateChange: (date: LocalDate) -> Unit,
    onSortClick: (TaskOrder) -> Unit,
    onDeleteTask: (Task) -> Unit,
    currentMonthYear: String,
    weekDay: String,
    currentOrder: TaskOrder,
    listWeekDay: List<Pair<String, String>>,
    listTask: List<Task>,
    currentDate: String
) {

    val sheetState = rememberModalBottomSheetState()

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }

    val dateDialogState = rememberMaterialDialogState()

    val calendarRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = listWeekDay.indexOfFirst {
            "${it.first} ${it.second}" == weekDay
        }
    )

    DatePickerDialog(dateDialogState = dateDialogState, onClick = {
        onDateChange(it)
    })

    LaunchedEffect(key1 = weekDay) {
        val index = listWeekDay.indexOfFirst {
            "${it.first} ${it.second}" == weekDay
        }
        calendarRowState.animateScrollToItem(index)
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomSheet)
                ModalBottomSheet(sheetState = sheetState,
                    dragHandle = null,
                    onDismissRequest = {
                        showBottomSheet = false
                    }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp)
                            .verticalScroll(scrollState)
                    ) {

                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "Sort order", fontSize = 18.sp, color = White
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioItem(
                                text = "Ascending",
                                isSelected = currentOrder.orderType == OrderType.Ascending,
                                onCLick = {
                                    onSortClick(currentOrder.copy(OrderType.Ascending))
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioItem(
                                text = "Descending",
                                isSelected = currentOrder.orderType == OrderType.Descending,
                                onCLick = {
                                    onSortClick(currentOrder.copy(OrderType.Descending))
                                }
                            )


                        }
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            thickness = 1.dp
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        val defferd = async { sheetState.hide() }
                                        defferd.await()
                                        showBottomSheet = false
                                    }
                                    onSortClick(TaskOrder.ByTimeAdded(currentOrder.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_alarm),
                                contentDescription = "Time",
                                tint = if (currentOrder is TaskOrder.ByTimeAdded) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Date added",
                                fontSize = 18.sp,
                                color = if (currentOrder is TaskOrder.ByTimeAdded) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )

                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        val defferd = async { sheetState.hide() }
                                        defferd.await()
                                        showBottomSheet = false
                                    }
                                    onSortClick(TaskOrder.ByPriority(currentOrder.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Priority",
                                tint = if (currentOrder is TaskOrder.ByPriority) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Priority",
                                fontSize = 18.sp,
                                color = if (currentOrder is TaskOrder.ByPriority) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )

                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        val defferd = async { sheetState.hide() }
                                        defferd.await()
                                        showBottomSheet = false
                                    }
                                    onSortClick(TaskOrder.ByTitle(currentOrder.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_title),
                                contentDescription = "Title",
                                tint = if (currentOrder is TaskOrder.ByTitle) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Title",
                                fontSize = 18.sp,
                                color =if (currentOrder is TaskOrder.ByTitle) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )

                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        val defferd = async { sheetState.hide() }
                                        defferd.await()
                                        showBottomSheet = false
                                    }
                                    onSortClick(TaskOrder.ByNumOfSubTasks(currentOrder.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_task),
                                contentDescription = "MaxSubTask",
                                tint = if (currentOrder is TaskOrder.ByNumOfSubTasks) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Number of subTasks",
                                fontSize = 18.sp,
                                color = if (currentOrder is TaskOrder.ByNumOfSubTasks) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )

                        }

                    }

                }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(Screen.TaskAddEdit.route + "?taskDate=$currentDate")


            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_task),
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.secondary

                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Add task", color = MaterialTheme.colorScheme.secondary)


            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        LightBlack,
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(bottom = 32.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Calendar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    IconButton(
                        onClick = {
                            showBottomSheet = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu_sort),
                            contentDescription = "Sort",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }

                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        dateDialogState.show()
                    }) {

                        Icon(
                            modifier = Modifier
                                .size(36.dp),
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "DateRange",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = currentMonthYear,
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                }

                Spacer(modifier = Modifier.height(32.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    state = calendarRowState
                ) {
                    items(listWeekDay.size) {
                        CalendarItem(
                            week = listWeekDay[it].first,
                            day = listWeekDay[it].second,
                            isSelected = listWeekDay[it].first + " " + listWeekDay[it].second == weekDay,
                            onClick = {
                                onWeekDayItemClick(listWeekDay[it].second)

                            }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                    }

                }


            }


            Text(
                modifier = Modifier.padding(16.dp),
                text = "Todays' task",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            listTask.forEach { task ->
                TaskCalItem(
                    title = task.title,
                    startTime = task.startTime,
                    date = task.dateOfTask,
                    isDone = task.isDone,
                    taskColor = task.taskColor,
                    numSubTask = task.subTasks.size,
                    numDoneSubTask = task.subTasks.filter {
                        it.isDone
                    }.size,
                    onTaskClick = {
                        navController.navigate(Screen.TaskAddEdit.route + "?taskId=${task.id}&taskDate=${task.dateOfTask}")
                    },
                    onDeleteTaskClick = {
                        onDeleteTask(task)
                    }
                )
            }


        }


    }


}
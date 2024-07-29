@file:OptIn(ExperimentalMaterial3Api::class)

package com.solo.tasky.feature_task.presentation.taskHome.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.R
import com.solo.tasky.feature_task.domain.models.TaskColor
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import com.solo.tasky.feature_task.presentation.taskCalendar.Component.CircleProgress
import com.solo.tasky.feature_task.presentation.taskCalendar.Component.DatePickerDialog
import com.solo.tasky.feature_task.presentation.taskCalendar.Component.RadioItem
import com.solo.tasky.feature_task.presentation.taskEditAdd.component.TransparentHintTextField
import com.solo.tasky.feature_task.presentation.taskHome.HomeScreenStates
import com.solo.tasky.ui.theme.LightText
import com.solo.tasky.ui.theme.White
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomeTaskScreen(
    modifier: Modifier = Modifier,
    homeScreenStates: HomeScreenStates,
    onProfileClick: () -> Unit,
    onSearchFocusChange: (FocusState) -> Unit,
    onSearchChange: (String) -> Unit,
    onEditTaskClick: (Int) -> Unit,
    onSortClick: (TaskOrder) -> Unit,
    onAddTask: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
) {


    val scope = rememberCoroutineScope()


    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val dateDialogState = rememberMaterialDialogState()

    val lazyStaggeredGirdState = rememberLazyStaggeredGridState()
    DatePickerDialog(dateDialogState = dateDialogState, onClick = {
        onDateChange(it)
    })
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
                                isSelected = homeScreenStates.order.orderType == OrderType.Ascending,
                                onCLick = {
                                    onSortClick(homeScreenStates.order.copy(OrderType.Ascending))
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioItem(
                                text = "Descending",
                                isSelected = homeScreenStates.order.orderType == OrderType.Descending,
                                onCLick = {
                                    onSortClick(homeScreenStates.order.copy(OrderType.Descending))
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
                                    onSortClick(TaskOrder.ByTimeAdded(homeScreenStates.order.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_alarm),
                                contentDescription = "Time",
                                tint = if (homeScreenStates.order is TaskOrder.ByTimeAdded) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Date added",
                                fontSize = 18.sp,
                                color = if (homeScreenStates.order is TaskOrder.ByTimeAdded) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
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
                                    onSortClick(TaskOrder.ByPriority(homeScreenStates.order.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Priority",
                                tint = if (homeScreenStates.order is TaskOrder.ByPriority) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Priority",
                                fontSize = 18.sp,
                                color = if (homeScreenStates.order is TaskOrder.ByPriority) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
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
                                    onSortClick(TaskOrder.ByTitle(homeScreenStates.order.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_title),
                                contentDescription = "Title",
                                tint = if (homeScreenStates.order is TaskOrder.ByTitle) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Title",
                                fontSize = 18.sp,
                                color = if (homeScreenStates.order is TaskOrder.ByTitle) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
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
                                    onSortClick(TaskOrder.ByNumOfSubTasks(homeScreenStates.order.orderType))
                                }
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_task),
                                contentDescription = "MaxSubTask",
                                tint = if (homeScreenStates.order is TaskOrder.ByNumOfSubTasks) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Number of subTasks",
                                fontSize = 18.sp,
                                color = if (homeScreenStates.order is TaskOrder.ByNumOfSubTasks) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary
                            )

                        }

                    }

                }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = lazyStaggeredGirdState.IsScrollingUp() ,
                enter = fadeIn() ,
                exit = fadeOut()
            ) {
                ExtendedFloatingActionButton(
                    onClick = onAddTask,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_task),
                        contentDescription = "Add Task",
                        tint = MaterialTheme.colorScheme.secondary

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Add task", color = MaterialTheme.colorScheme.secondary)

                }
            }

        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(paddingValues)
//                .verticalScroll(scrollState)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tasks",
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

            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = "Hi,${homeScreenStates.username}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = "Be productive today",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Box(modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransparentHintTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        text = homeScreenStates.searchBarState.query,
                        hint = "Search task",
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                        hintColor = Color.Gray,
                        isHintVisible = (!homeScreenStates.searchBarState.isFocus && homeScreenStates.searchBarState.query.isBlank()),
                        onValueChange = onSearchChange,
                        onFocusChange = onSearchFocusChange
                    )

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = White

                    )

                }
            }


            //task call item

            //random color for progress and date icon
            val randomTaskColor = TaskColor(
                MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.tertiary,
                MaterialTheme.colorScheme.tertiary
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))

                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Task Progress",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "${homeScreenStates.tasks.filter { it.isDone }.size}/${homeScreenStates.tasks.size} task done",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Start,
                            color = LightText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = randomTaskColor.color,
                                    RoundedCornerShape(16.dp)
                                )
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    dateDialogState.show()
                                }
                                .padding(6.dp)
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = homeScreenStates.date.substring(10),
                                fontSize = 14.sp,

                                color = MaterialTheme.colorScheme.onPrimary
                            )


                        }


                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    CircleProgress(
                        modifier = Modifier.size(60.dp),
                        fullPercentage = homeScreenStates.tasks.size,
                        currentPercentage = homeScreenStates.tasks.filter { it.isDone }.size,
                        isDone = true,
                        fontWeightEnable = true,
                        progressStroke = 18f,
                        percentageSize = 16,
                        taskColor = randomTaskColor

                    )


                }
            }


            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = lazyStaggeredGirdState
            ) {
                items(homeScreenStates.tasks) {
                    HomeTaskItem(
                        task = it,
                        onEditTaskClick = { id ->
                            onEditTaskClick(id)
                        }
                    )
                }
            }


        }


    }
}


@Composable
fun LazyStaggeredGridState.IsScrollingUp(
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
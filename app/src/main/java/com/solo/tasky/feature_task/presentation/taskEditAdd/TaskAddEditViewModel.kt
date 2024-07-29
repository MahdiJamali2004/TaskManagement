package com.solo.tasky.feature_task.presentation.taskEditAdd

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_task.domain.models.InvalidTaskException
import com.solo.tasky.feature_task.domain.models.SubTask
import com.solo.tasky.feature_task.domain.models.Task
import com.solo.tasky.feature_task.domain.task_notifications.InitializeNotificationWorker
import com.solo.tasky.feature_task.domain.usecases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskAddEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskUseCases: TaskUseCases,
    private val initializeNotificationWorker: InitializeNotificationWorker
) : ViewModel() {

    private var _title = MutableStateFlow(EditTextState())
    val title = _title.asStateFlow()

    private var _description = MutableStateFlow(EditTextState(hint = "Enter text..."))
    val description = _description.asStateFlow()

    private var _subTasks =
        MutableStateFlow<MutableList<MutableState<SubTaskState>>>(mutableListOf())
    val subTasks = _subTasks.asStateFlow()

    private var _taskEditAddState = MutableStateFlow(TaskEditAddState())
    val taskEditAddState = _taskEditAddState.asStateFlow()


    private var _currentDate = savedStateHandle.getStateFlow<String>(
        "taskDate",
        LocalDate.now().format(DateTimeFormatter.ofPattern("MMM, yyyy EEE dd"))
    )
    val currentDate = _currentDate

    private var _endDate = MutableStateFlow(_currentDate.value)
    val endDate = _endDate.asStateFlow()

    private var _taskException = MutableSharedFlow<String>()
    val taskException = _taskException.asSharedFlow()


    private var _currentId = savedStateHandle.getStateFlow<Int>("taskId", -1)


    init {

        viewModelScope.launch {
            _currentId.collectLatest { id ->
                if (id != -1) {
                    val task = taskUseCases.getTasksUseCase.getTaskById(id)
                    _title.value = title.value.copy(
                        text = task.title,
                        isHintVisible = task.title.isBlank()
                    )
                    _description.value = description.value.copy(
                        text = task.description,
                        isHintVisible = task.title.isBlank()
                    )


                    //converting List<SubTask> to MutableList<SubTaskState>
                    val list = task.subTasks.map {
                        mutableStateOf(
                            SubTaskState(
                                text = it.title,
                                isDone = it.isDone,
                                isHintVisible = it.title.isBlank(),
                                hint = "Enter subTask"
                            )
                        )
                    }
                    _subTasks.value = list.toMutableList()

                    _taskEditAddState.value = taskEditAddState.value.copy(
                        startTime = task.startTime,
                        isDone = task.isDone,
                        priority = task.priority,
                        taskColor = task.taskColor
                    )


                }


            }


        }


    }

    fun onEvents(addEditTaskEvents: AddEditTaskEvents) {

        when (addEditTaskEvents) {


            is AddEditTaskEvents.DescriptionChange -> {
                _description.value = description.value.copy(
                    text = addEditTaskEvents.text
                )
            }

            is AddEditTaskEvents.TitleChange -> {
                _title.value = title.value.copy(
                    text = addEditTaskEvents.text
                )

            }

            is AddEditTaskEvents.AddSubTask -> {
                _subTasks.value = subTasks.value.toMutableList().apply {
                    add(mutableStateOf(SubTaskState(isHintVisible = true)))
                }
            }

            is AddEditTaskEvents.DeleteSubTask -> {
                _subTasks.value = subTasks.value.toMutableList().apply {
                    removeAt(addEditTaskEvents.index)
                }
            }


            is AddEditTaskEvents.UpdateSubTask -> {

                _subTasks.value[addEditTaskEvents.index].value =
                    subTasks.value[addEditTaskEvents.index].value.copy(
                        text = addEditTaskEvents.text,
                        isDone = addEditTaskEvents.isDone,
                        /// isHintVisible = addEditTaskEvents.text.isBlank()
                    )


            }


            is AddEditTaskEvents.FocusStateChangeSubTask -> {
                _subTasks.value[addEditTaskEvents.index].value =
                    subTasks.value[addEditTaskEvents.index].value.copy(
                        isHintVisible = !addEditTaskEvents.focusState.isFocused && title.value.text.isBlank()
                    )
            }


            is AddEditTaskEvents.StartTimeChange -> {

                _taskEditAddState.value = taskEditAddState.value.copy(
                    startTime = getTime(addEditTaskEvents.time)
                )

            }

            is AddEditTaskEvents.DoneClick -> {
                _taskEditAddState.value = taskEditAddState.value.copy(
                    isDone = !addEditTaskEvents.isDone
                )
            }

            is AddEditTaskEvents.DateChange -> {
                savedStateHandle["taskDate"] =
                    addEditTaskEvents.date.format(DateTimeFormatter.ofPattern("MMM, yyyy EEE dd"))
            }

            is AddEditTaskEvents.PriorityChange -> {
                _taskEditAddState.value = taskEditAddState.value.copy(
                    priority = addEditTaskEvents.priority
                )
            }


            is AddEditTaskEvents.TaskColorChange -> {
                _taskEditAddState.value = taskEditAddState.value.copy(
                    taskColor = addEditTaskEvents.taskColor
                )
            }

            is AddEditTaskEvents.DescriptionFocusChange -> {
                _description.value = description.value.copy(
                    isHintVisible = !addEditTaskEvents.focusState.isFocused && description.value.text.isBlank()
                )
            }

            is AddEditTaskEvents.TitleFocusChange -> {
                _title.value = title.value.copy(
                    isHintVisible = !addEditTaskEvents.focusState.isFocused && title.value.text.isBlank()
                )

            }


            is AddEditTaskEvents.InsertTask -> {
                viewModelScope.launch {
                    try {
                        val task = Task(
                            id = if (_currentId.value == -1) null else _currentId.value,
                            title = title.value.text,
                            description = description.value.text,
                            taskColor = taskEditAddState.value.taskColor,
                            dateOfTask = _currentDate.value,
                            priority = taskEditAddState.value.priority,
                            startTime = taskEditAddState.value.startTime,
                            isDone = taskEditAddState.value.isDone,
                            subTasks = subTasks.value.map {
                                SubTask(title = it.value.text, isDone = it.value.isDone)
                            }.toMutableList(),
                            timestamp = System.currentTimeMillis()
                        )
                        taskUseCases.insertTaskUseCase.insertTask(task)
                        initializeNotificationWorker.setTaskNotification(task)
                    } catch (exception: InvalidTaskException) {
                        _taskException.emit(exception.message!!)
                    }
                }
            }


        }


    }

    fun getDoneSubTaskSize(): Int {
        return subTasks.value.filter {
            it.value.isDone
        }.size

    }


    fun getTime(time: LocalTime): String {
        return LocalTime.of(
            time.hour,
            time.minute,
            time.second
        ).format(DateTimeFormatter.ofPattern("HH:mm a"))

    }


}






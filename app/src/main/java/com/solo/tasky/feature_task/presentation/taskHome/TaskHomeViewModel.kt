package com.solo.tasky.feature_task.presentation.taskHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo.tasky.feature_login.domain.repository.LoginRepository
import com.solo.tasky.feature_task.data.util.Constants
import com.solo.tasky.feature_task.domain.usecases.TaskUseCases
import com.solo.tasky.feature_task.domain.util.OrderType
import com.solo.tasky.feature_task.domain.util.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskHomeViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    loginRepository: LoginRepository
) : ViewModel() {

    private var _homeScreenStates = MutableStateFlow(HomeScreenStates())
    val homeScreenStates = _homeScreenStates.asStateFlow()


    init {


        _homeScreenStates.value =
            homeScreenStates.value.copy(username = loginRepository.getUsername())


        viewModelScope.launch {

            taskUseCases.getTasksUseCase
                .getTasksSortedBy(
                    TaskOrder.ByPriority(OrderType.Descending),
                    LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                ).collectLatest {
                    _homeScreenStates.value = homeScreenStates.value.copy(
                        tasks = it
                    )

                }
        }


    }

    fun onEvents(homeScreenEvents: HomeScreenEvents) {
        when (homeScreenEvents) {
            is HomeScreenEvents.TasksOrderChange -> {
                _homeScreenStates.value = homeScreenStates.value.copy(
                    order = homeScreenEvents.taskOrder
                )
                viewModelScope.launch {
                    taskUseCases.getTasksUseCase
                        .getTasksSortedBy(homeScreenEvents.taskOrder, homeScreenStates.value.date)
                        .collectLatest {
                            _homeScreenStates.value = homeScreenStates.value.copy(
                                tasks = it
                            )
                        }
                }
            }

            is HomeScreenEvents.CurrentDateChange -> {
                val stringFormatDate =
                    homeScreenEvents.date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
                _homeScreenStates.value = homeScreenStates.value.copy(
                    date = stringFormatDate
                )
                taskUseCases.getTasksUseCase.getTasksSortedBy(
                    homeScreenStates.value.order,
                    stringFormatDate
                ).onEach {
                    _homeScreenStates.value = homeScreenStates.value.copy(
                        tasks = it
                    )
                }.launchIn(viewModelScope)

            }

            is HomeScreenEvents.SearchChange -> {
                _homeScreenStates.value = homeScreenStates.value.copy(
                    searchBarState = homeScreenStates.value.searchBarState.copy(query = homeScreenEvents.text)
                )
                viewModelScope.launch {

                    if (homeScreenStates.value.searchBarState.query.isBlank()) {
                        taskUseCases.getTasksUseCase.getTasksSortedBy(
                            homeScreenStates.value.order,
                            homeScreenStates.value.date
                        ).collectLatest {
                            _homeScreenStates.value = homeScreenStates.value.copy(
                                tasks = it
                            )
                        }

                    } else {
                        taskUseCases.getTasksUseCase
                            .getTaskByTitle(homeScreenStates.value.searchBarState.query)
                            .collectLatest {
                                _homeScreenStates.value = homeScreenStates.value.copy(
                                    tasks = it
                                )
                            }
                    }
                }

            }

            is HomeScreenEvents.SearchFocusChange -> {
                _homeScreenStates.value = homeScreenStates.value.copy(
                    searchBarState = SearchBarState(
                        isFocus = homeScreenEvents.focusState.isFocused,
                        query = homeScreenStates.value.searchBarState.query
                    )

                )

            }
        }

    }


}
package com.solo.tasky.feature_task.domain.util

sealed class TaskOrder(val orderType: OrderType) {
    class ByTitle(orderType: OrderType) : TaskOrder(orderType)
    class ByTimeAdded(orderType: OrderType) : TaskOrder(orderType)
    class ByPriority(orderType: OrderType) : TaskOrder(orderType)
    class ByNumOfSubTasks(orderType: OrderType) : TaskOrder(orderType)


    fun copy(orderType: OrderType) :TaskOrder {

      return when(this) {
            is ByTitle -> TaskOrder.ByTitle(orderType)
            is ByPriority -> TaskOrder.ByPriority(orderType)
            is ByTimeAdded -> TaskOrder.ByTimeAdded(orderType)
            is ByNumOfSubTasks -> TaskOrder.ByNumOfSubTasks(orderType)
        }

    }
}
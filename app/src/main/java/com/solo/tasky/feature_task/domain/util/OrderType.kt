package com.solo.tasky.feature_task.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
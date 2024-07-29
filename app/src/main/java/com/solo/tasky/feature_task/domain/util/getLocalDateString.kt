package com.solo.tasky.feature_task.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun localDateString ( localDate: LocalDate) : String {
    val pattern =DateTimeFormatter.ofPattern("yyyy MMMM dd")
    return LocalDate.of(localDate.year ,localDate.month ,localDate.dayOfMonth).format(pattern)
}

fun localDateStringUI(localDate: LocalDate) : String {
    val pattern = DateTimeFormatter.ofPattern("MM,yyyy")
    return LocalDate.of(localDate.year , localDate.month ,localDate.dayOfMonth).format(pattern)
}

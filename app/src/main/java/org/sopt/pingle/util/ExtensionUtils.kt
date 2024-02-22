package org.sopt.pingle.util

import android.content.res.Resources
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf

inline fun <reified T> List<Flow<T>>.combineAll(): Flow<List<T>> {
    return when (size) {
        0 -> flowOf(emptyList())
        else -> combineTransform(this) { flows ->
            emit(flows.toList())
        }
    }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

package by.masarnovsky.releasedemon.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField

fun tryDate(date: String): LocalDate {
    val default = LocalDate.of(1970, 1, 1)

    return listOfNotNull(tryFull(date), tryYearAndMonth(date), tryYear(date), default).first()
}

fun tryFull(date: String): LocalDate? {
    val tryFull = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return try {
        LocalDate.parse(date, tryFull)
    } catch (e: DateTimeParseException) {
        null
    }
}

fun tryYearAndMonth(date: String): LocalDate? {
    val tryYearAndMonth = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM")
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .toFormatter()
    return try {
        LocalDate.parse(date, tryYearAndMonth)
    } catch (e: DateTimeParseException) {
        null
    }
}

fun tryYear(date: String): LocalDate? {
    val tryYear = DateTimeFormatterBuilder()
        .appendPattern("yyyy")
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .toFormatter()
    return try {
        LocalDate.parse(date, tryYear)
    } catch (e: DateTimeParseException) {
        null
    }
}

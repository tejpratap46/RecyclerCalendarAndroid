package com.tejpratapsingh.recyclercalendar.model

import java.util.*

open class RecyclerCalendarConfiguration(
    val calenderViewType: CalenderViewType,
    val calendarLocale: Locale,
    val includeMonthHeader: Boolean
) {
    enum class CalenderViewType {
        HORIZONTAL, VERTICAL
    }

    enum class START_DAY_OF_WEEK(val offset: Int) {
        SATURDAY(1), SUNDAY(0), MONDAY(-1)
    }

    var weekStartOffset: START_DAY_OF_WEEK = START_DAY_OF_WEEK.SUNDAY
}
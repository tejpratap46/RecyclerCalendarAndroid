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
}
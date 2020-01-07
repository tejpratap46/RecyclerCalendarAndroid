package com.tejpratapsingh.recyclercalendar

import java.util.*

class RecyclerCalendarConfiguration(val calenderViewType: CalenderViewType, val calendarLocale: Locale, val includeMonthHeader: Boolean) {
    enum class CalenderViewType {
        HORIZONTAL, VERTICAL
    }
}
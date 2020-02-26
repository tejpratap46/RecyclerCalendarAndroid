package com.tejpratapsingh.recyclercalendar.model

import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.util.*

class RecyclerCalenderViewItem constructor(
    // date of calender item
    var date: Date,
    // Span size of cell for grid view
    // Month Header has span size of 7 (full week)
    // For offset for new month, we use empty cell which can have span from 0-6
    var spanSize: Int,
    // For offset of new month
    var isEmpty: Boolean,
    // Header is simply a Month name cell
    var isHeader: Boolean
) {
    override fun toString(): String {
        return String.format(
            Locale.getDefault(),
            "date: %s, spanSize: %d, isEmpty: %s, isHeader: %s",
            CalendarUtils.getGmt(date),
            spanSize,
            isEmpty.toString(),
            isHeader.toString()
        )
    }
}
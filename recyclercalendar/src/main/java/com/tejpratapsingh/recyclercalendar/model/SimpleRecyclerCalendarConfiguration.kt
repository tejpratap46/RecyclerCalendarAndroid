package com.tejpratapsingh.recyclercalendar.model

import java.util.*
import kotlin.collections.HashMap

class SimpleRecyclerCalendarConfiguration(
    calenderViewType: CalenderViewType,
    calendarLocale: Locale,
    includeMonthHeader: Boolean,
    selectionMode: SelectionMode
) : RecyclerCalendarConfiguration(
    calenderViewType, calendarLocale, includeMonthHeader
) {
    var selectionMode: SelectionMode = SelectionModeNone()
    init {
        this.selectionMode = selectionMode
    }

    open class SelectionMode {
        enum class TYPE {
            NONE, SINGLE, MULTIPLE, RANGE
        }

        var selectionType: TYPE = TYPE.NONE
    }

    class SelectionModeNone : SelectionMode() {
        init {
            this.selectionType = TYPE.NONE
        }
    }

    class SelectionModeSingle(selectedDate: Date?) : SelectionMode() {
        var selectedDate: Date? = Date()

        init {
            this.selectionType = TYPE.SINGLE
            this.selectedDate = selectedDate
        }
    }

    class SelectionModeMultiple(selectionStartDateList: HashMap<String, Date>) : SelectionMode() {
        var selectionStartDateList: HashMap<String, Date> = HashMap()

        init {
            this.selectionType = TYPE.MULTIPLE
            this.selectionStartDateList = selectionStartDateList
        }
    }

    class SelectionModeRange(selectionStartDate: Date, selectionEndDate: Date) : SelectionMode() {
        var selectionStartDate: Date = Date()
        var selectionEndDate: Date = Date()

        init {
            this.selectionType = TYPE.RANGE
            this.selectionStartDate = selectionStartDate
            this.selectionEndDate = selectionEndDate
        }
    }

}
package com.tejpratapsingh.recyclercalendaractivity.simple

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tejpratapsingh.recyclercalendar.adapter.InfiniteRecyclerCalenderAdapter
import com.tejpratapsingh.recyclercalendar.model.InfiniteRecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendar.views.InfiniteRecyclerCalendarView
import com.tejpratapsingh.recyclercalendaractivity.R
import java.util.*
import kotlin.collections.HashMap

class InfiniteRecyclerCalendarActivity : AppCompatActivity() {

    private var calenderView: InfiniteRecyclerCalendarView? = null
    private var selectionMode: InfiniteRecyclerCalendarConfiguration.SelectionMode? = null
    private var calendarViewType: RecyclerCalendarConfiguration.CalenderViewType =
        RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infinite_recycler_calendar)

        calenderView = findViewById(R.id.calendarRecyclerView)

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val buttonSetting: ImageButton = findViewById(R.id.buttonSimpleSettings)
        val layoutSettingContainer: LinearLayout = findViewById(R.id.layoutSettingContainer)

        buttonSetting.setOnClickListener {
            if (layoutSettingContainer.visibility == View.VISIBLE) {
                layoutSettingContainer.visibility = View.GONE
            } else {
                layoutSettingContainer.visibility = View.VISIBLE
            }
        }

        val radioViewTypeVertical: RadioButton = findViewById(R.id.radioViewTypeVertical)
        val radioViewTypeHorizontal: RadioButton = findViewById(R.id.radioViewTypeHorizontal)

        val radioSelectNone: RadioButton = findViewById(R.id.radioSelectionNone)
        val radioSelectSingle: RadioButton = findViewById(R.id.radioSelectionSingle)
        val radioSelectMultiple: RadioButton = findViewById(R.id.radioSelectionMultiple)
        val radioSelectRange: RadioButton = findViewById(R.id.radioSelectionRange)

        radioViewTypeVertical.setOnClickListener {
            // Switch to Vertical View
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }

        radioViewTypeHorizontal.setOnClickListener {
            // Switch to Horizontal View
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }

        radioSelectNone.setOnClickListener {
            // Switch to Selection Mode NONE
            selectionMode = InfiniteRecyclerCalendarConfiguration.SelectionModeNone()

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time

            )
        }
        radioSelectSingle.setOnClickListener {
            // Switch to Selection Mode SINGLE
            selectionMode =
                InfiniteRecyclerCalendarConfiguration.SelectionModeSingle(selectedDate = Date())

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }
        radioSelectMultiple.setOnClickListener {
            // Switch to Selection Mode MULTIPLE
            selectionMode = InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple(
                selectionStartDateList = HashMap()
            )

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }
        radioSelectRange.setOnClickListener {
            // Switch to Selection Mode RANGE

            // Range Starts from todayDate and ends on 5 days from today
            val selectionEndCal = Calendar.getInstance()
            selectionEndCal.time = date
            selectionEndCal.add(Calendar.DATE, 5)

            selectionMode = InfiniteRecyclerCalendarConfiguration.SelectionModeRange(
                selectionStartDate = date,
                selectionEndDate = selectionEndCal.time
            )

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }

        // Set None As Default
        radioSelectNone.callOnClick()
    }

    private fun refreshCalendarCalendar(
        startDate: Date,
        endDate: Date
    ) {
        if (calenderView == null || selectionMode == null) {
            return
        }
        val configuration: InfiniteRecyclerCalendarConfiguration =
            InfiniteRecyclerCalendarConfiguration(
                calenderViewType = calendarViewType,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true,
                selectionMode = selectionMode!!
            )

        calenderView!!.initialise(
            configuration,
            object : InfiniteRecyclerCalenderAdapter.OnDateSelected {
                override fun onDateSelected(date: Date) {
                    Toast.makeText(
                        calenderView!!.context,
                        "Date Selected: ${CalendarUtils.getGmt(date)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}

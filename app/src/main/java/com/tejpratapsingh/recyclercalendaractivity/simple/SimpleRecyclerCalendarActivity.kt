package com.tejpratapsingh.recyclercalendaractivity.simple

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tejpratapsingh.recyclercalendar.adapter.SimpleRecyclerCalendarAdapter
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.SimpleRecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendar.views.SimpleRecyclerCalendarView
import com.tejpratapsingh.recyclercalendaractivity.R
import java.util.*
import kotlin.collections.HashMap

class SimpleRecyclerCalendarActivity : AppCompatActivity() {

    private var calenderView: SimpleRecyclerCalendarView? = null
    private var selectionMode: SimpleRecyclerCalendarConfiguration.SelectionMode? = null
    private var calendarViewType: RecyclerCalendarConfiguration.CalenderViewType =
        RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_recycler_calendar)

        calenderView = findViewById(R.id.calendarRecyclerView)

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val radioViewTypeVertical: RadioButton = findViewById(R.id.radioViewTypeVertical)
        val radioViewTypeHorizontal: RadioButton = findViewById(R.id.radioViewTypeHorizontal)

        val radioSelectNone: RadioButton = findViewById(R.id.radioSelectionNone)
        val radioSelectSingle: RadioButton = findViewById(R.id.radioSelectionSingle)
        val radioSelectMultiple: RadioButton = findViewById(R.id.radioSelectionMultiple)
        val radioSelectRange: RadioButton = findViewById(R.id.radioSelectionRange)

        radioViewTypeVertical.setOnClickListener {
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }

        radioViewTypeHorizontal.setOnClickListener {
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }

        radioSelectNone.setOnClickListener {
            selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeNone()

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time

            )
        }
        radioSelectSingle.setOnClickListener {
            selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeSingle(Date())

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }
        radioSelectMultiple.setOnClickListener {
            selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeMultiple(
                HashMap()
            )

            refreshCalendarCalendar(
                startDate = startCal.time,
                endDate = endCal.time
            )
        }
        radioSelectRange.setOnClickListener {

            val selectionEndCal = Calendar.getInstance()
            selectionEndCal.time = date
            selectionEndCal.add(Calendar.DATE, 5)

            selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeRange(
                selectionStartDate = Date(),
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
        val configuration: SimpleRecyclerCalendarConfiguration =
            SimpleRecyclerCalendarConfiguration(
                calenderViewType = calendarViewType,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true,
                selectionMode = selectionMode!!
            )

        calenderView!!.initialise(
            startDate,
            endDate,
            configuration,
            object : SimpleRecyclerCalendarAdapter.OnDateSelected {
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

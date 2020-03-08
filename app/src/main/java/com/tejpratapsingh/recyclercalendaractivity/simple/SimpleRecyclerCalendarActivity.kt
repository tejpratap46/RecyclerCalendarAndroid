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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_recycler_calendar)

        val calenderView: SimpleRecyclerCalendarView = findViewById(R.id.calendarRecyclerView)

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val radioSelectNone: RadioButton = findViewById(R.id.radioSelectionNone)
        val radioSelectSingle: RadioButton = findViewById(R.id.radioSelectionSingle)
        val radioSelectMultiple: RadioButton = findViewById(R.id.radioSelectionMultiple)
        val radioSelectRange: RadioButton = findViewById(R.id.radioSelectionRange)

        radioSelectNone.setOnClickListener {
            val configuration: SimpleRecyclerCalendarConfiguration =
                SimpleRecyclerCalendarConfiguration(
                    calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                    calendarLocale = Locale.getDefault(),
                    includeMonthHeader = true,
                    selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeNone()
                )

            calenderView.initialise(
                startCal.time,
                endCal.time,
                configuration,
                object : SimpleRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        Toast.makeText(
                            calenderView.context,
                            "Date Selected: ${CalendarUtils.getGmt(date)}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
        radioSelectSingle.setOnClickListener {
            val configuration: SimpleRecyclerCalendarConfiguration =
                SimpleRecyclerCalendarConfiguration(
                    calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                    calendarLocale = Locale.getDefault(),
                    includeMonthHeader = true,
                    selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeSingle(Date())
                )

            calenderView.initialise(
                startCal.time,
                endCal.time,
                configuration,
                object : SimpleRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        Toast.makeText(
                            calenderView.context,
                            "Date Selected: ${CalendarUtils.getGmt(date)}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
        radioSelectMultiple.setOnClickListener {
            val configuration: SimpleRecyclerCalendarConfiguration =
                SimpleRecyclerCalendarConfiguration(
                    calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                    calendarLocale = Locale.getDefault(),
                    includeMonthHeader = true,
                    selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeMultiple(
                        HashMap()
                    )
                )

            calenderView.initialise(
                startCal.time,
                endCal.time,
                configuration,
                object : SimpleRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        Toast.makeText(
                            calenderView.context,
                            "Date Selected: ${CalendarUtils.getGmt(date)}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
        radioSelectRange.setOnClickListener {
            val selectionEndCal = Calendar.getInstance()
            selectionEndCal.time = date
            selectionEndCal.add(Calendar.DATE, 5)

            val configuration: SimpleRecyclerCalendarConfiguration =
                SimpleRecyclerCalendarConfiguration(
                    calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                    calendarLocale = Locale.getDefault(),
                    includeMonthHeader = true,
                    selectionMode = SimpleRecyclerCalendarConfiguration.SelectionModeRange(
                        selectionStartDate = Date(),
                        selectionEndDate = selectionEndCal.time
                    )
                )

            calenderView.initialise(
                startCal.time,
                endCal.time,
                configuration,
                object : SimpleRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        Toast.makeText(
                            calenderView.context,
                            "Date Selected: ${CalendarUtils.getGmt(date)}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        // Set None As Default
        radioSelectNone.callOnClick()
    }
}

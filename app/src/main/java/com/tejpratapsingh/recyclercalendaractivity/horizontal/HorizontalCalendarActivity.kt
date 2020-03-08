package com.tejpratapsingh.recyclercalendaractivity.horizontal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendaractivity.R
import java.util.*

class HorizontalCalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_calendar)

        val calendarRecyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView)
        val textViewSelectedDate: TextView = findViewById(R.id.textViewSelectedDate)

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val configuration: RecyclerCalendarConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )

        textViewSelectedDate.text =
            CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = date,
                format = CalendarUtils.LONG_DATE_FORMAT
            ) ?: ""

        val calendarAdapterHorizontal: HorizontalRecyclerCalendarAdapter =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                selectedDate = date,
                dateSelectListener = object : HorizontalRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        textViewSelectedDate.text =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = date,
                                format = CalendarUtils.LONG_DATE_FORMAT
                            )
                                ?: ""
                    }
                }
            )

        calendarRecyclerView.adapter = calendarAdapterHorizontal

        val snapHelper = PagerSnapHelper() // Or LinearSnapHelper
        snapHelper.attachToRecyclerView(calendarRecyclerView)
    }
}

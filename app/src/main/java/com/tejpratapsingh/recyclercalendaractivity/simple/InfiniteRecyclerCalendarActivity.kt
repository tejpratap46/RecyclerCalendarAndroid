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

class InfiniteRecyclerCalendarActivity : AppCompatActivity() {

    private var calenderView: InfiniteRecyclerCalendarView? = null
    private var selectionMode: InfiniteRecyclerCalendarConfiguration.SelectionMode? = null
    private var calendarViewType: RecyclerCalendarConfiguration.CalenderViewType =
        RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infinite_recycler_calendar)

        calenderView = findViewById(R.id.calendarRecyclerView)

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

        radioViewTypeVertical.setOnClickListener {
            // Switch to Vertical View
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL

            refreshCalendarCalendar()
        }

        radioViewTypeHorizontal.setOnClickListener {
            // Switch to Horizontal View
            calendarViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL

            refreshCalendarCalendar()
        }

        selectionMode = InfiniteRecyclerCalendarConfiguration.SelectionModeNone()

        refreshCalendarCalendar()
    }

    private fun refreshCalendarCalendar() {
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

        configuration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

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

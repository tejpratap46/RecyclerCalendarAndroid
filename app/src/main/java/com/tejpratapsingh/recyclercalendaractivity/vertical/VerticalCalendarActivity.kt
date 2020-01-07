package com.tejpratapsingh.recyclercalendaractivity.vertical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendaractivity.R
import java.util.*

class VerticalCalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_calendar)

        val toolbar: Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeButtonEnabled(true)

        val calendarRecyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView);

        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 6)

        val configuration: RecyclerCalendarConfiguration = RecyclerCalendarConfiguration(
            calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
            calendarLocale = Locale.UK,
            includeMonthHeader = true
        )

        val calendarAdapterVertical: VerticalRecyclerCalendarAdapter =
            VerticalRecyclerCalendarAdapter(
                startCal.time,
                endCal.time,
                configuration
            );

        calendarRecyclerView.adapter = calendarAdapterVertical
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.tejpratapsingh.recyclercalendaractivity.horizontal

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.RecyclerCalendarBaseAdapter
import com.tejpratapsingh.recyclercalendar.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.RecyclerCalenderViewItem
import com.tejpratapsingh.recyclercalendaractivity.R
import java.text.DateFormatSymbols
import java.util.*

class HorizontalRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    configuration: RecyclerCalendarConfiguration
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_horizontal, parent, false)
        return MonthCalendarViewHolder(
            view
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem
    ) {
        val monthViewHolder: MonthCalendarViewHolder = holder as MonthCalendarViewHolder
        monthViewHolder.itemView.visibility = View.VISIBLE

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            val month: String = getMonth(selectedCalendar.get(Calendar.MONTH)) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            monthViewHolder.textViewDay.text = year.toString()
            monthViewHolder.textViewDate.text = month

            monthViewHolder.itemView.setOnClickListener(null)
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            val day: String = getDay(selectedCalendar.get(Calendar.DAY_OF_WEEK)) ?: ""

            monthViewHolder.textViewDay.text = day

            monthViewHolder.textViewDate.text =
                String.format(Locale.getDefault(), "%d", selectedCalendar.get(Calendar.DATE))
        }
    }

    class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDate)
    }

    private fun getMonth(month: Int): String? {
        val mDateFormatSymbols: DateFormatSymbols =
            DateFormatSymbols.getInstance(Locale.UK)
        return mDateFormatSymbols.months.get(month)
    }

    private fun getDay(day: Int): String? {
        val mDateFormatSymbols: DateFormatSymbols =
            DateFormatSymbols.getInstance(Locale.UK)
        val dayStr: String = mDateFormatSymbols.weekdays.get(day)
        return dayStr.substring(0, if (dayStr.length >= 3) 3 else dayStr.length)
    }
}
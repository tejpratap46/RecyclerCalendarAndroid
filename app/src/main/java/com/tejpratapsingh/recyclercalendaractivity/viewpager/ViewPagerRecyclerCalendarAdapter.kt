package com.tejpratapsingh.recyclercalendaractivity.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.adapter.RecyclerCalendarBaseAdapter
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalenderViewItem
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendaractivity.R
import com.tejpratapsingh.recyclercalendaractivity.model.SimpleEvent
import java.util.*

class ViewPagerRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    configuration: RecyclerCalendarConfiguration,
    val eventMap: HashMap<Int, SimpleEvent>,
    val dateSelectListener: ViewPagerRecyclerCalendarAdapter.OnDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {

    interface OnDateSelected {
        fun onDateSelected(date: Date, event: SimpleEvent?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_view_pager, parent, false)
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
        monthViewHolder.progressBar.visibility = View.GONE

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance(Locale.UK)
            selectedCalendar.time = calendarItem.date

            val month: String = CalendarUtils.getMonth(selectedCalendar.get(Calendar.MONTH)) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            monthViewHolder.textViewDay.text = year.toString()
            monthViewHolder.textViewDate.text = month
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance(Locale.UK)
            calendarDate.time = calendarItem.date

            val day: String = CalendarUtils.getDay(calendarDate.get(Calendar.DAY_OF_WEEK)) ?: ""

            val dateInt: Int =
                (CalendarUtils.dateStringFromFormat(calendarDate.time, CalendarUtils.DB_DATE_FORMAT)
                    ?: "0").toInt()

            if (eventMap.contains(dateInt)) {
                monthViewHolder.progressBar.visibility = View.VISIBLE
                monthViewHolder.progressBar.progress = eventMap.get(dateInt)!!.progress
            }

            monthViewHolder.textViewDay.text = day

            monthViewHolder.textViewDate.text =
                String.format(Locale.getDefault(), "%d", calendarDate.get(Calendar.DATE))

            monthViewHolder.itemView.setOnClickListener {
                dateSelectListener.onDateSelected(calendarItem.date, eventMap[dateInt])
            }
        }
    }

    class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemViewPagerDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemViewPagerDate)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarCalenderItemViewPager);
    }
}
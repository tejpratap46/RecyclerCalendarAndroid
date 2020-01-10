package com.tejpratapsingh.recyclercalendar.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.R
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalenderViewItem
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.text.DateFormatSymbols
import java.util.*

class SimpleRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    configuration: RecyclerCalendarConfiguration,
    private var selectedDate: Date,
    private val dateSelectListener: OnDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {
    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_calendar, parent, false)
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
        val context: Context = monthViewHolder.itemView.context
        monthViewHolder.itemView.visibility = View.VISIBLE

        monthViewHolder.itemView.setOnClickListener(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            monthViewHolder.itemView.background = null
        } else {
            monthViewHolder.itemView.setBackgroundDrawable(null)
        }
        monthViewHolder.textViewDay.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )
        monthViewHolder.textViewDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            val month: String = CalendarUtils.getMonth(selectedCalendar.get(Calendar.MONTH)) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            monthViewHolder.textViewDay.text = year.toString()
            monthViewHolder.textViewDate.text = month

            monthViewHolder.itemView.setOnClickListener(null)
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance()
            calendarDate.time = calendarItem.date

            val stringCalendarTimeFormat: String =
                CalendarUtils.dateStringFromFormat(calendarItem.date, CalendarUtils.DB_DATE_FORMAT)
                    ?: ""
            val stringSelectedTimeFormat: String =
                CalendarUtils.dateStringFromFormat(selectedDate, CalendarUtils.DB_DATE_FORMAT) ?: ""

            if (stringCalendarTimeFormat == stringSelectedTimeFormat) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    monthViewHolder.itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.layout_round_corner_filled)
                } else {
                    monthViewHolder.itemView.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.layout_round_corner_filled
                        )
                    )
                }
                monthViewHolder.textViewDay.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
                monthViewHolder.textViewDate.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
            }

            val day: String = CalendarUtils.getDay(calendarDate.get(Calendar.DAY_OF_WEEK)) ?: ""

            monthViewHolder.textViewDay.text = day

            monthViewHolder.textViewDate.text =
                String.format(Locale.getDefault(), "%d", calendarDate.get(Calendar.DATE))

            monthViewHolder.itemView.setOnClickListener {
                selectedDate = calendarItem.date
                dateSelectListener.onDateSelected(calendarItem.date)
                notifyDataSetChanged()
            }
        }
    }

    class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDate)
    }
}
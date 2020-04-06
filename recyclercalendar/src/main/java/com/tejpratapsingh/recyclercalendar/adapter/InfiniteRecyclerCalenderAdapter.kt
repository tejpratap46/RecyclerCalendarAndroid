package com.tejpratapsingh.recyclercalendar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.R
import com.tejpratapsingh.recyclercalendar.model.InfiniteRecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.SimpleRecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendar.views.SimpleRecyclerCalendarView
import java.util.*

class InfiniteRecyclerCalenderAdapter(
    private val dateSelectListener: OnDateSelected,
    private val configuration: InfiniteRecyclerCalendarConfiguration
) :
    RecyclerView.Adapter<InfiniteRecyclerCalenderAdapter.InfiniteViewHolder>() {
    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfiniteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_infinite_calendar, parent, false)
        return InfiniteViewHolder(itemView = view)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(
        holder: InfiniteViewHolder,
        position: Int
    ) {
        val midPosition: Int = Int.MAX_VALUE / 2
        val currentMonth: Int = position - midPosition

        val startCal: Calendar = Calendar.getInstance(configuration.calendarLocale)
        startCal.add(Calendar.MONTH, currentMonth)
        startCal.set(Calendar.DATE, 1)

        val endCal: Calendar = Calendar.getInstance(configuration.calendarLocale)
        endCal.time = startCal.time
        endCal.add(Calendar.MONTH, 1)
        endCal.add(Calendar.DATE, -1)

        // Convert InfiniteRecyclerCalendarConfiguration TO SimpleRecyclerCalendarConfiguration
        val selectionMode: SimpleRecyclerCalendarConfiguration.SelectionMode =
            when (configuration.selectionMode) {
                is InfiniteRecyclerCalendarConfiguration.SelectionModeNone -> {
                    SimpleRecyclerCalendarConfiguration.SelectionModeNone()
                }
                is InfiniteRecyclerCalendarConfiguration.SelectionModeSingle -> {
                    val selectionModeSingle: InfiniteRecyclerCalendarConfiguration.SelectionModeSingle =
                        configuration.selectionMode as InfiniteRecyclerCalendarConfiguration.SelectionModeSingle
                    SimpleRecyclerCalendarConfiguration.SelectionModeSingle(selectionModeSingle.selectedDate)
                }
                is InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple -> {
                    val selectionModeMultiple: InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple =
                        configuration.selectionMode as InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple
                    SimpleRecyclerCalendarConfiguration.SelectionModeMultiple(selectionModeMultiple.selectionStartDateList)
                }
                is InfiniteRecyclerCalendarConfiguration.SelectionModeRange -> {
                    val selectionModeRange: InfiniteRecyclerCalendarConfiguration.SelectionModeRange =
                        configuration.selectionMode as InfiniteRecyclerCalendarConfiguration.SelectionModeRange
                    SimpleRecyclerCalendarConfiguration.SelectionModeRange(
                        selectionStartDate = selectionModeRange.selectionStartDate,
                        selectionEndDate = selectionModeRange.selectionEndDate
                    )
                }
                else -> {
                    SimpleRecyclerCalendarConfiguration.SelectionModeNone()
                }
            }

        val simpleRecyclerCalendarConfiguration: SimpleRecyclerCalendarConfiguration =
            SimpleRecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                calendarLocale = configuration.calendarLocale,
                includeMonthHeader = configuration.includeMonthHeader,
                selectionMode = selectionMode
            )

        holder.simpleRecyclerCalendarView.initialise(
            startDate = startCal.time,
            endDate = endCal.time,
            configuration = simpleRecyclerCalendarConfiguration,
            dateSelectListener = object : SimpleRecyclerCalendarAdapter.OnDateSelected {
                override fun onDateSelected(date: Date) {
                    val currentDateString: String =
                        CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = date,
                            format = CalendarUtils.DB_DATE_FORMAT
                        )
                            ?: ""

                    when (configuration.selectionMode) {
                        is InfiniteRecyclerCalendarConfiguration.SelectionModeSingle -> {
                            val selectionModeSingle: InfiniteRecyclerCalendarConfiguration.SelectionModeSingle =
                                configuration.selectionMode as InfiniteRecyclerCalendarConfiguration.SelectionModeSingle
                            // Set new selected date
                            selectionModeSingle.selectedDate = date
                            simpleRecyclerCalendarConfiguration.selectionMode =
                                SimpleRecyclerCalendarConfiguration.SelectionModeSingle(
                                    selectedDate = selectionModeSingle.selectedDate
                                )
                        }
                        is InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple -> {
                            val selectionModeMultiple: InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple =
                                configuration.selectionMode as InfiniteRecyclerCalendarConfiguration.SelectionModeMultiple
                            selectionModeMultiple.selectionStartDateList[currentDateString] = date

                            simpleRecyclerCalendarConfiguration.selectionMode =
                                SimpleRecyclerCalendarConfiguration.SelectionModeMultiple(
                                    selectionStartDateList = selectionModeMultiple.selectionStartDateList
                                )
                        }
                        is InfiniteRecyclerCalendarConfiguration.SelectionModeRange -> {
                            Log.d("SelectionModeRange", "SelectionModeRange")
                            holder.simpleRecyclerCalendarView.getConfiguration()?.let {
                                val selectionModeRange: SimpleRecyclerCalendarConfiguration.SelectionModeRange =
                                    it.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange

                                configuration.selectionMode =
                                    InfiniteRecyclerCalendarConfiguration.SelectionModeRange(
                                        selectionStartDate = selectionModeRange.selectionStartDate,
                                        selectionEndDate = selectionModeRange.selectionEndDate
                                    )
                            }
                        }
                        else -> {
                            SimpleRecyclerCalendarConfiguration.SelectionModeNone()
                        }
                    }

                    dateSelectListener.onDateSelected(date)

                    notifyDataSetChanged()
                }
            })
    }

    class InfiniteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val simpleRecyclerCalendarView: SimpleRecyclerCalendarView =
            itemView.findViewById(R.id.simpleCalenderRecyclerView)
    }

    /**
     * Set LayoutManager of recycler view to GridLayoutManager with span of 7 (week)
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if (configuration.calenderViewType == RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL) {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        } else {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}
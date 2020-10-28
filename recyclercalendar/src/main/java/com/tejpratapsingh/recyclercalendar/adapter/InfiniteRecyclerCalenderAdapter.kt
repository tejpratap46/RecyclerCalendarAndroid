package com.tejpratapsingh.recyclercalendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
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

    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }

    private var snapHelper: SnapHelper = PagerSnapHelper()

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

        holder.simpleRecyclerCalendarView.setRecycledViewPool(this.viewPool)
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

                    dateSelectListener.onDateSelected(date)
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

            recyclerView.onFlingListener = null

            snapHelper = PagerSnapHelper() // Or LinearSnapHelper
            snapHelper.attachToRecyclerView(recyclerView)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            recyclerView.onFlingListener = null

            snapHelper.attachToRecyclerView(null)
        }
    }
}
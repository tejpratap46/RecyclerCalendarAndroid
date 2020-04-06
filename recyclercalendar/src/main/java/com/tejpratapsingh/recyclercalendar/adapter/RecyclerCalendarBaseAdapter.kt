package com.tejpratapsingh.recyclercalendar.adapter

import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalenderViewItem
import java.util.*


abstract class RecyclerCalendarBaseAdapter(
    startDate: Date,
    endDate: Date,
    private val configuration: RecyclerCalendarConfiguration
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mHandlerThread: MyHandlerThread =
        MyHandlerThread(
            RecyclerCalendarBaseAdapter::class.java.simpleName
        )
    private val handler = Handler()

    private var calendarItemList: LinkedList<RecyclerCalenderViewItem> = LinkedList()

    init {
        val startCalendar = Calendar.getInstance(configuration.calendarLocale)
        val endCalendar = Calendar.getInstance(configuration.calendarLocale)
        if (configuration.calenderViewType == RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL) {
            startCalendar.time = startDate
            var thisDayOfWeek: Int = startCalendar.get(Calendar.DAY_OF_WEEK)

            if (thisDayOfWeek - 1 < 0) {
                thisDayOfWeek = 7 + (thisDayOfWeek - 1)
                startCalendar.add(Calendar.DATE, -1 * thisDayOfWeek)
            } else {
                startCalendar.add(Calendar.DATE, -1 * (thisDayOfWeek - 1))
            }
            startCalendar[Calendar.HOUR_OF_DAY] = 0
            startCalendar[Calendar.MINUTE] = 0
            startCalendar[Calendar.SECOND] = 0
            startCalendar[Calendar.MILLISECOND] = 0

            endCalendar.time = endDate
            endCalendar[Calendar.DAY_OF_MONTH] = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            endCalendar[Calendar.HOUR_OF_DAY] = 0
            endCalendar[Calendar.MINUTE] = 0
            endCalendar[Calendar.SECOND] = 0
            endCalendar[Calendar.MILLISECOND] = 0
        } else {
            startCalendar.time = startDate
            startCalendar[Calendar.DAY_OF_MONTH] = 1
            startCalendar[Calendar.HOUR_OF_DAY] = 0
            startCalendar[Calendar.MINUTE] = 0
            startCalendar[Calendar.SECOND] = 0
            startCalendar[Calendar.MILLISECOND] = 0

            endCalendar.time = endDate
            endCalendar[Calendar.DAY_OF_MONTH] = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            endCalendar[Calendar.HOUR_OF_DAY] = 0
            endCalendar[Calendar.MINUTE] = 0
            endCalendar[Calendar.SECOND] = 0
            endCalendar[Calendar.MILLISECOND] = 0
        }

        val runnable = Runnable {
            while (startCalendar.time.before(endCalendar.time) || startCalendar.time == endCalendar.time) {
                val dayOfMonth: Int = startCalendar.get(Calendar.DAY_OF_MONTH)
                var dayOfWeek: Int = startCalendar.get(Calendar.DAY_OF_WEEK)
                if (configuration.calenderViewType == RecyclerCalendarConfiguration.CalenderViewType.VERTICAL && dayOfMonth == 1) { // First Day of month
                    if (configuration.includeMonthHeader) {
                        val calendarEmptyHeader: RecyclerCalenderViewItem =
                            RecyclerCalenderViewItem(
                                date = startCalendar.time,
                                spanSize = 7,
                                isEmpty = false,
                                isHeader = true
                            )
                        calendarItemList.add(calendarEmptyHeader)
                    }

                    val calendarEmptyWeek: RecyclerCalenderViewItem =
                        RecyclerCalenderViewItem(
                            date = startCalendar.time,
                            spanSize = 1,
                            isEmpty = true,
                            isHeader = false
                        )
                    if (dayOfWeek - 1 < 0) {
                        dayOfWeek = 7 + (dayOfWeek - 1)
                        calendarEmptyWeek.spanSize = dayOfWeek
                    } else {
                        calendarEmptyWeek.spanSize = dayOfWeek - 1
                    }
                    if (calendarEmptyWeek.spanSize > 0) {
                        // Is span size is greater then 0, then add empty cell
                        calendarItemList.add(calendarEmptyWeek)
                    }
                }
                val calendarDateItem: RecyclerCalenderViewItem =
                    RecyclerCalenderViewItem(
                        date = startCalendar.time,
                        spanSize = 1,
                        isEmpty = false,
                        isHeader = false
                    )
                calendarItemList.add(calendarDateItem)
                startCalendar.add(Calendar.DATE, 1)
            }
            handler.post(Runnable {
                notifyDataSetChanged()
            })
        }

        mHandlerThread.start()
        mHandlerThread.prepareHandler()
        mHandlerThread.postTask(runnable)
    }

    override fun getItemCount(): Int {
        return calendarItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(
            holder = holder,
            position = position,
            calendarItem = calendarItemList[position]
        )
    }

    abstract fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem
    )

    fun getItem(position: Int): RecyclerCalenderViewItem? {
        return if (position < calendarItemList.size) {
            calendarItemList[position];
        } else {
            null
        }
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
            val gridLayoutManager =
                GridLayoutManager(recyclerView.context, 7)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val spanSize: Int? = getItem(position)?.spanSize
                    if (spanSize != null) {
                        return spanSize
                    }
                    return 0
                }
            }

            recyclerView.layoutManager = gridLayoutManager
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mHandlerThread.quit()
    }

    // Thread handler
    class MyHandlerThread constructor(name: String) :
        HandlerThread(name) {
        private var handler: Handler? = null
        fun postTask(task: Runnable) {
            handler!!.post(task)
        }

        fun prepareHandler() {
            handler = Handler(looper)
        }
    }
}
package com.tejpratapsingh.recyclercalendar.adapter

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.R
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalenderViewItem
import com.tejpratapsingh.recyclercalendar.model.SimpleRecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.util.*

class SimpleRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    private val configuration: SimpleRecyclerCalendarConfiguration,
    private val dateSelectListener: OnDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {
    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }

    enum class POSITION {
        NONE, // Date is not part of Selection
        SINGLE, // Single Selected Date
        START, // Selection Start
        MIDDLE, // Selection Middle
        END // Selection End
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_calendar, parent, false)
        return SimpleCalendarViewHolder(
            view
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem
    ) {
        val simpleViewHolder: SimpleCalendarViewHolder = holder as SimpleCalendarViewHolder
        val context: Context = simpleViewHolder.itemView.context
        simpleViewHolder.itemView.visibility = View.VISIBLE

        simpleViewHolder.itemView.setOnClickListener(null)

        highlightDate(simpleViewHolder, POSITION.NONE)

        simpleViewHolder.textViewDay.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )
        simpleViewHolder.textViewDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )

        simpleViewHolder.textViewDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        simpleViewHolder.textViewDate.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            val month: String = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = selectedCalendar.time,
                format = CalendarUtils.DISPLAY_MONTH_FORMAT
            ) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            simpleViewHolder.textViewDay.text = year.toString()
            simpleViewHolder.textViewDate.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
            simpleViewHolder.textViewDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
            simpleViewHolder.textViewDate.text = month

            simpleViewHolder.itemView.setOnClickListener(null)

        } else if (calendarItem.isEmpty) {
            simpleViewHolder.itemView.visibility = View.GONE
            simpleViewHolder.textViewDay.text = ""
            simpleViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance()
            calendarDate.time = calendarItem.date

            val currentDateString: String =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarItem.date,
                    format = CalendarUtils.DB_DATE_FORMAT
                )
                    ?: ""

            val currentWeekDay: String = CalendarUtils.dateStringFromFormat(
                locale = configuration.calendarLocale,
                date = calendarDate.time,
                format = CalendarUtils.DISPLAY_WEEK_DAY_FORMAT
            ) ?: ""

            simpleViewHolder.textViewDay.text = currentWeekDay

            simpleViewHolder.textViewDate.text =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DISPLAY_DATE_FORMAT
                ) ?: ""

            when (configuration.selectionMode) {
                is SimpleRecyclerCalendarConfiguration.SelectionModeSingle -> {
                    val selectedDate: Date? =
                        (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeSingle).selectedDate

                    if (selectedDate != null) {
                        val stringSelectedTimeFormat: String =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = selectedDate,
                                format = CalendarUtils.DB_DATE_FORMAT
                            ) ?: ""

                        if (currentDateString == stringSelectedTimeFormat) {
                            highlightDate(simpleViewHolder, POSITION.SINGLE)
                        }

                        simpleViewHolder.itemView.setOnClickListener {
                            (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeSingle).selectedDate =
                                calendarItem.date
                            dateSelectListener.onDateSelected(calendarItem.date)
                            notifyDataSetChanged()
                        }
                    }
                }
                is SimpleRecyclerCalendarConfiguration.SelectionModeMultiple -> {
                    val selectionStartDateList: HashMap<String, Date> =
                        (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeMultiple).selectionStartDateList

                    val calenderMultipleSelection: Calendar =
                        Calendar.getInstance(configuration.calendarLocale)
                    calenderMultipleSelection.time = calendarItem.date
                    calenderMultipleSelection.add(Calendar.DATE, -1)

                    val yesterdayDateString: String =
                        CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = calenderMultipleSelection.time,
                            format = CalendarUtils.DB_DATE_FORMAT
                        )
                            ?: ""

                    calenderMultipleSelection.add(Calendar.DATE, 2)
                    val tomorrowDateString: String =
                        CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = calenderMultipleSelection.time,
                            format = CalendarUtils.DB_DATE_FORMAT
                        )
                            ?: ""

                    // Set Highlight background based on position of selected date
                    if (selectionStartDateList[currentDateString] != null) {
                        if (selectionStartDateList[yesterdayDateString] != null && selectionStartDateList[tomorrowDateString] != null) {
                            highlightDate(simpleViewHolder, POSITION.MIDDLE)
                        } else if (selectionStartDateList[yesterdayDateString] != null) {
                            highlightDate(simpleViewHolder, POSITION.END)
                        } else if (selectionStartDateList[tomorrowDateString] != null) {
                            highlightDate(simpleViewHolder, POSITION.START)
                        } else {
                            highlightDate(simpleViewHolder, POSITION.SINGLE)
                        }
                    }

                    simpleViewHolder.itemView.setOnClickListener {
                        if (selectionStartDateList[currentDateString] == null) {
                            selectionStartDateList[currentDateString] = calendarItem.date
                        } else {
                            selectionStartDateList.remove(currentDateString)
                        }
                        (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeMultiple).selectionStartDateList =
                            selectionStartDateList
                        dateSelectListener.onDateSelected(calendarItem.date)
                        notifyDataSetChanged()
                    }
                }
                is SimpleRecyclerCalendarConfiguration.SelectionModeRange -> {
                    val startDate: Date =
                        (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionStartDate

                    val endDate: Date =
                        (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionEndDate

                    val selectedDateInt: Int = currentDateString.toInt()

                    val startDateInt: Int = (CalendarUtils.dateStringFromFormat(
                        locale = configuration.calendarLocale,
                        date = startDate,
                        format = CalendarUtils.DB_DATE_FORMAT
                    ) ?: Int.MAX_VALUE.toString()).toInt()

                    val endDateInt: Int = (CalendarUtils.dateStringFromFormat(
                        locale = configuration.calendarLocale,
                        date = endDate,
                        format = CalendarUtils.DB_DATE_FORMAT
                    ) ?: Int.MIN_VALUE.toString()).toInt()

                    if (selectedDateInt in startDateInt..endDateInt) {
                        when (selectedDateInt) {
                            startDateInt -> {
                                highlightDate(simpleViewHolder, POSITION.START)
                            }
                            endDateInt -> {
                                highlightDate(simpleViewHolder, POSITION.END)
                            }
                            else -> {
                                highlightDate(simpleViewHolder, POSITION.MIDDLE)
                            }
                        }
                    }

                    simpleViewHolder.itemView.setOnClickListener {
                        if (selectedDateInt < startDateInt) {
                            (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionStartDate =
                                calendarItem.date
                        } else if (selectedDateInt > endDateInt) {
                            (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionEndDate =
                                calendarItem.date
                        } else if (selectedDateInt in (startDateInt + 1) until endDateInt) {
                            if (startDate.time - calendarItem.date.time > calendarItem.date.time - endDate.time) {
                                // Selected date is closer to END date, so move end date
                                (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionStartDate =
                                    calendarItem.date
                            } else {
                                // Selected date is closer is START date, so move start date
                                (configuration.selectionMode as SimpleRecyclerCalendarConfiguration.SelectionModeRange).selectionEndDate =
                                    calendarItem.date
                            }
                        }
                        dateSelectListener.onDateSelected(calendarItem.date)
                        notifyDataSetChanged()
                    }
                }
                else -> {
                    // Else None
                    simpleViewHolder.itemView.setOnClickListener {
                        dateSelectListener.onDateSelected(calendarItem.date)
                    }
                }
            }
        }
    }

    class SimpleCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val layoutStartPadding: View =
            itemView.findViewById(R.id.layoutCalenderItemSimpleStartPadding)
        val layoutEndPadding: View = itemView.findViewById(R.id.layoutCalenderItemSimpleEndPadding)
        val layoutContainer: View =
            itemView.findViewById(R.id.layoutCalenderItemSimpleContainer)
        val layoutContainerInner: LinearLayout =
            itemView.findViewById(R.id.layoutCalenderItemSimpleContainerInner)
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemSimpleDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemSimpleDate)
    }

    private fun highlightDate(monthViewHolder: SimpleCalendarViewHolder, position: POSITION) {
        val context: Context = monthViewHolder.itemView.context

        if (position == POSITION.START) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                monthViewHolder.layoutContainer.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )

                monthViewHolder.layoutContainerInner.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
            } else {
                monthViewHolder.layoutContainer.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )
                )

                monthViewHolder.layoutContainerInner.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
                )
            }

            monthViewHolder.layoutStartPadding.visibility = View.VISIBLE
            monthViewHolder.layoutEndPadding.visibility = View.GONE
        } else if (position == POSITION.END) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                monthViewHolder.layoutContainer.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )
                monthViewHolder.layoutContainerInner.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
            } else {
                monthViewHolder.layoutContainer.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )
                )

                monthViewHolder.layoutContainerInner.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
                )
            }

            monthViewHolder.layoutStartPadding.visibility = View.GONE
            monthViewHolder.layoutEndPadding.visibility = View.VISIBLE
        } else if (position == POSITION.MIDDLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                monthViewHolder.layoutContainer.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )
                monthViewHolder.layoutContainerInner.background = null
            } else {
                monthViewHolder.layoutContainer.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_middle_filled
                    )
                )
                monthViewHolder.layoutContainerInner.setBackgroundDrawable(null)
            }

            monthViewHolder.layoutStartPadding.visibility = View.GONE
            monthViewHolder.layoutEndPadding.visibility = View.GONE
        } else if (position == POSITION.SINGLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                monthViewHolder.layoutContainer.background = null
                monthViewHolder.layoutContainerInner.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
            } else {
                monthViewHolder.layoutContainer.setBackgroundDrawable(null)
                monthViewHolder.layoutContainerInner.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.layout_round_corner_filled
                    )
                )
            }

            monthViewHolder.layoutStartPadding.visibility = View.GONE
            monthViewHolder.layoutEndPadding.visibility = View.GONE
        } else if (position == POSITION.NONE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                monthViewHolder.layoutContainer.background = null
                monthViewHolder.layoutContainerInner.background = null
            } else {
                monthViewHolder.layoutContainer.setBackgroundDrawable(null)
                monthViewHolder.layoutContainerInner.setBackgroundDrawable(null)
            }

            monthViewHolder.layoutStartPadding.visibility = View.GONE
            monthViewHolder.layoutEndPadding.visibility = View.GONE
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
}
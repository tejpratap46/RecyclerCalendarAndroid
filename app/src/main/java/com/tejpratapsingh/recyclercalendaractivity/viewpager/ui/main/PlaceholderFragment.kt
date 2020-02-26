package com.tejpratapsingh.recyclercalendaractivity.viewpager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import com.tejpratapsingh.recyclercalendaractivity.R
import com.tejpratapsingh.recyclercalendaractivity.model.SimpleEvent
import com.tejpratapsingh.recyclercalendaractivity.viewpager.ViewPagerRecyclerCalendarAdapter
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private val eventMap: HashMap<Int, SimpleEvent> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view_pager_calendar, container, false)
        val sectionNumber: Int = arguments!!.get(ARG_SECTION_NUMBER) as Int
        val calendarRecyclerView: RecyclerView = root.findViewById(R.id.calendarRecyclerView)
        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()
        startCal.time = date
        startCal.add(Calendar.MONTH, sectionNumber)

        val configuration: RecyclerCalendarConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = false
            )

        // Some Random Events
        for (i in 0..30 step 3) {
            val eventCal = Calendar.getInstance()
            eventCal.add(Calendar.DATE, i * 3)
            val eventDate: Int =
                (CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = eventCal.time,
                    format = CalendarUtils.DB_DATE_FORMAT
                )
                    ?: "0").toInt()
            eventMap[eventDate] = SimpleEvent(
                date = eventCal.time,
                title = "Event #$i",
                color = ContextCompat.getColor(root.context, R.color.colorAccent),
                progress = i * 3
            )
        }

        val calendarAdapterViewPager: ViewPagerRecyclerCalendarAdapter =
            ViewPagerRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = startCal.time,
                configuration = configuration,
                eventMap = eventMap,
                dateSelectListener = object : ViewPagerRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date, event: SimpleEvent?) {
                        val selectedDate: String =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = date,
                                format = CalendarUtils.LONG_DATE_FORMAT
                            )
                                ?: ""

                        if (event != null) {
                            AlertDialog.Builder(activity!!)
                                .setTitle("Event Clicked")
                                .setMessage(
                                    String.format(
                                        Locale.getDefault(),
                                        "Date: %s\n\nEvent: %s\n\nProgress: %s",
                                        selectedDate,
                                        event.title,
                                        event.progress
                                    )
                                )
                                .create()
                                .show()
                        }
                    }
                }
            );

        calendarRecyclerView.adapter = calendarAdapterViewPager
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
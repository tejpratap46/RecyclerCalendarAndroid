package com.tejpratapsingh.recyclercalendaractivity.viewpager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendaractivity.R
import com.tejpratapsingh.recyclercalendaractivity.viewpager.ViewPagerRecyclerCalendarAdapter
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

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

        val configuration: RecyclerCalendarConfiguration = RecyclerCalendarConfiguration(
            calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.VERTICAL,
            calendarLocale = Locale.UK,
            includeMonthHeader = false
        )

        val calendarAdapterViewPager: ViewPagerRecyclerCalendarAdapter =
            ViewPagerRecyclerCalendarAdapter(
                startCal.time,
                startCal.time,
                configuration
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
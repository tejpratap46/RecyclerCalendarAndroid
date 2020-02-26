package com.tejpratapsingh.recyclercalendaractivity.viewpager.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.util.*

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val date = Date()
        date.time = System.currentTimeMillis()
        val selectedCalendar = Calendar.getInstance(Locale.getDefault())
        selectedCalendar.time = date
        selectedCalendar.add(Calendar.MONTH, position)

        val month: String = CalendarUtils.dateStringFromFormat(
            locale = Locale.getDefault(),
            date = selectedCalendar.time,
            format = CalendarUtils.DISPLAY_MONTH_FORMAT
        ) ?: ""
        val year = selectedCalendar[Calendar.YEAR].toLong()

        return String.format(Locale.getDefault(), "%s / %d", month, year)
    }

    override fun getCount(): Int {
        // Show total pages.
        return 24
    }
}
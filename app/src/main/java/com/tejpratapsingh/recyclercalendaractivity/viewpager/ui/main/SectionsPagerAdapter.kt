package com.tejpratapsingh.recyclercalendaractivity.viewpager.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.text.DateFormatSymbols
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
        val selectedCalendar = Calendar.getInstance(Locale.UK)
        selectedCalendar.time = date
        selectedCalendar.add(Calendar.MONTH, position)

        val month: String = getMonth(selectedCalendar.get(Calendar.MONTH)) ?: ""
        val year = selectedCalendar[Calendar.YEAR].toLong()

        return String.format(Locale.getDefault(), "%s / %d", month, year)
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 24
    }

    private fun getMonth(month: Int): String? {
        val mDateFormatSymbols: DateFormatSymbols =
            DateFormatSymbols.getInstance(Locale.UK)
        return mDateFormatSymbols.months.get(month)
    }
}
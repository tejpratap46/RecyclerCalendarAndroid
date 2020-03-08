package com.tejpratapsingh.recyclercalendaractivity.viewpager

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tejpratapsingh.recyclercalendaractivity.R
import com.tejpratapsingh.recyclercalendaractivity.viewpager.ui.main.SectionsPagerAdapter

class ViewPagerCalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_calendar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                // showAlertMsg("LTR")
                tabs.layoutDirection = View.LAYOUT_DIRECTION_LTR;
            }
        }
    }
}
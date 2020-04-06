package com.tejpratapsingh.recyclercalendaractivity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.tejpratapsingh.recyclercalendaractivity.horizontal.HorizontalCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.simple.InfiniteRecyclerCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.simple.SimpleRecyclerCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.vertical.VerticalCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.viewpager.ViewPagerCalendarActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutWeekCalendar: LinearLayout = findViewById(R.id.layoutWeekCalendarActivity);
        layoutWeekCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, HorizontalCalendarActivity::class.java)
            startActivity(intent)
        }

        val layoutMonthCalendar: LinearLayout = findViewById(R.id.layoutMonthCalendarActivity);
        layoutMonthCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, VerticalCalendarActivity::class.java)
            startActivity(intent)
        }

        val layoutPageCalendar: LinearLayout = findViewById(R.id.layoutPageCalendarActivity);
        layoutPageCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, ViewPagerCalendarActivity::class.java)
            startActivity(intent)
        }

        val layoutSimpleCalendar: LinearLayout = findViewById(R.id.layoutSimpleCalendarActivity);
        layoutSimpleCalendar.setOnClickListener {
            val intent: Intent =
                Intent(this@MainActivity, SimpleRecyclerCalendarActivity::class.java)
            startActivity(intent)
        }

        val layoutInfiniteCalendar: LinearLayout =
            findViewById(R.id.layoutInfiniteCalendarActivity);
        layoutInfiniteCalendar.setOnClickListener {
            val intent: Intent =
                Intent(this@MainActivity, InfiniteRecyclerCalendarActivity::class.java)
            startActivity(intent)
        }
    }
}

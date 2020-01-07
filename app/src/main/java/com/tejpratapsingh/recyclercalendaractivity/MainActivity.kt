package com.tejpratapsingh.recyclercalendaractivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tejpratapsingh.recyclercalendaractivity.vertical.VerticalCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.horizontal.HorizontalCalendarActivity
import com.tejpratapsingh.recyclercalendaractivity.viewpager.ViewPagerCalendarActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonWeekCalendar: Button = findViewById(R.id.buttonWeekCalendarActivity);
        buttonWeekCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, HorizontalCalendarActivity::class.java)
            startActivity(intent)
        }

        val buttonMonthCalendar: Button = findViewById(R.id.buttonMonthCalendarActivity);
        buttonMonthCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, VerticalCalendarActivity::class.java)
            startActivity(intent)
        }

        val buttonPageCalendar: Button = findViewById(R.id.buttonPageCalendarActivity);
        buttonPageCalendar.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, ViewPagerCalendarActivity::class.java)
            startActivity(intent)
        }
    }
}

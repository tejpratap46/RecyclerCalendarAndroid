package com.tejpratapsingh.recyclercalendar.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.tejpratapsingh.recyclercalendar.adapter.InfiniteRecyclerCalenderAdapter
import com.tejpratapsingh.recyclercalendar.model.InfiniteRecyclerCalendarConfiguration

class InfiniteRecyclerCalendarView : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun initialise(
        configuration: InfiniteRecyclerCalendarConfiguration,
        dateSelectListener: InfiniteRecyclerCalenderAdapter.OnDateSelected
    ) {
        this.setItemViewCacheSize(10)
        this.setHasFixedSize(true)

        val infiniteRecyclerCalendarAdapter: InfiniteRecyclerCalenderAdapter =
            InfiniteRecyclerCalenderAdapter(
                configuration = configuration,
                dateSelectListener = dateSelectListener
            )

        adapter = infiniteRecyclerCalendarAdapter

        scrollToPosition(Int.MAX_VALUE / 2)
    }
}
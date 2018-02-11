package com.example.nyoun_000.todateproject.Decorator

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*


/**
 * Created by nyoun_000 on 2018-02-08.
 */
class HighlightWeekendsDecorator : DayViewDecorator {

    private val calendar = Calendar.getInstance()
    private val highlightDrawable: Drawable

    init {
        highlightDrawable = ColorDrawable(color)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        day.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(highlightDrawable)
    }

    companion object {
        private val color = Color.parseColor("#228BC34A")
    }
}
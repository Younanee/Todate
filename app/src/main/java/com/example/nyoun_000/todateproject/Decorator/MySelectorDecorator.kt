package com.example.nyoun_000.todateproject.Decorator

import android.app.Activity
import android.graphics.drawable.Drawable
import com.example.nyoun_000.todateproject.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade



/**
 * Created by nyoun_000 on 2018-02-08.
 */
class MySelectorDecorator(context: Activity) : DayViewDecorator {

    private val drawable: Drawable

    init {
        drawable = context.resources.getDrawable(R.drawable.my_selector)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
    }
}
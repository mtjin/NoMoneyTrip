package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.R
import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    //private val drawable: Drawable?
    var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        //view.setSelectionDrawable(drawable!!)
        view.setDaysDisabled(true)
    }

    init {
        // You can set background for Decorator via drawable here
       // drawable = ContextCompat.getDrawable(context!!, R.drawable.checkbox_off_background)
    }
}
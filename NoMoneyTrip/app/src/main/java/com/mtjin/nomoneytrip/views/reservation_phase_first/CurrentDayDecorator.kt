package com.mtjin.nomoneytrip.views.reservation_phase_first

import android.R
import android.app.Activity
import android.graphics.drawable.Drawable
import com.mtjin.nomoneytrip.utils.getMyDrawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable = context?.getMyDrawable(R.drawable.ic_menu_close_clear_cancel)!!
    private var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        //view.setSelectionDrawable(drawable!!)
        view.setBackgroundDrawable(drawable)
        view.setDaysDisabled(true)
    }

    init {
        // You can set background for Decorator via drawable here
    }
}
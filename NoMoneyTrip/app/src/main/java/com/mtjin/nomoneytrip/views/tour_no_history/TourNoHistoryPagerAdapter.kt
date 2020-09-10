package com.mtjin.nomoneytrip.views.tour_no_history

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class TourNoHistoryPagerAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragments = ArrayList<TourNoHistoryRecommendFragment>()
    fun addItem(item: TourNoHistoryRecommendFragment) {
        fragments.add(item)
    }

    fun addItems(items: List<TourNoHistoryRecommendFragment>) {
        fragments.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): TourNoHistoryRecommendFragment {
        return fragments[position]
    }

    fun clear() {
        fragments.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
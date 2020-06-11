package com.mtjin.nomoneytrip.views.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomePagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private val items: ArrayList<HomePagerItemFragment> = ArrayList()
    override fun getItem(position: Int): Fragment = items[position]

    override fun getCount(): Int {
        return items.size
    }
}
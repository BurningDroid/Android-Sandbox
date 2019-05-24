package com.youknow.customcalendar.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class CalendarPagerAdapter(
    fm: FragmentManager,
    private val startIdx: Int
) : FragmentStatePagerAdapter(fm) {

    private val timeList: MutableList<Long> = mutableListOf()

    init {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -2)
        timeList.add(calendar.timeInMillis)
        calendar.add(Calendar.MONTH, 1)
        timeList.add(calendar.timeInMillis)
        calendar.add(Calendar.MONTH, 1)
        timeList.add(calendar.timeInMillis)
        calendar.add(Calendar.MONTH, 1)
        timeList.add(calendar.timeInMillis)
        calendar.add(Calendar.MONTH, 1)
        timeList.add(calendar.timeInMillis)
    }

    override fun getItem(position: Int): Fragment = CalendarFragment().apply {
        arguments = Bundle().apply { putLong(TIME_IN_MILLIS, timeList[position % timeList.size]) }
    }

    override fun getCount(): Int = Integer.MAX_VALUE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val index = position % timeList.size
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, position - startIdx)
        timeList[index] = calendar.timeInMillis
        return super.instantiateItem(container, index)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val virtualPosition = position % timeList.size
        super.destroyItem(container, virtualPosition, `object`)
    }


}
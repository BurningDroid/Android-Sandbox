package com.youknow.customcalendar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.youknow.customcalendar.R

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var calendarPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarPager = findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val startIdx = Integer.MAX_VALUE / 2 - 1
        val pagerAdapter = CalendarPagerAdapter(supportFragmentManager, startIdx)
        calendarPager.adapter = pagerAdapter
        calendarPager.currentItem = startIdx

        calendarPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

            }
        })
    }
}

package com.youknow.customcalendar.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.youknow.customcalendar.R
import com.youknow.customcalendar.extentions.getDateList
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*

const val TIME_IN_MILLIS = "timeInMillis"

class CalendarFragment : Fragment() {

    private val dateFormat = SimpleDateFormat("yyyy MMMM")
    private val mAdapter: CalendarAdapter by lazy { CalendarAdapter(context!!, gvCalendar) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calendar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gvCalendar!!.adapter = mAdapter
        arguments?.getLong(TIME_IN_MILLIS)?.let { time ->
            tvMonth.text = dateFormat.format(Date(time))
            mAdapter.setData(time, time.getDateList())
        }
    }

}

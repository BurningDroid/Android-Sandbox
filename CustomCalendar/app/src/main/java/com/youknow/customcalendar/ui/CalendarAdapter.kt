package com.youknow.customcalendar.ui

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.youknow.customcalendar.R
import com.youknow.customcalendar.extentions.getDayOfMonth
import com.youknow.customcalendar.extentions.month

private const val ROW_NUMBER = 6

class CalendarAdapter(
    private val context: Context,
    private val gridView: GridView,
    private val dateList: MutableList<Long> = mutableListOf()
) : BaseAdapter() {

    private var month: Long = 0

    fun setData(baseTime: Long, dateList: List<Long>) {
        month = baseTime.month()
        this.dateList.clear()
        this.dateList.addAll(dateList)

        notifyDataSetChanged()
    }

    override fun getCount() = dateList.size

    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View? = convertView
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_calendar, null)
            v.tag = CalendarHolder().apply {
                tvDate = v!!.findViewById(R.id.tv_date)
            }
        }

        val date = dateList[position]
        val holder = (v?.tag as CalendarHolder).apply {
            tvDate?.text = date.getDayOfMonth()
        }

        if (month == date.month()) {
            holder.tvDate?.setBackgroundColor(context.resources.getColor(getLabelColor(date)))
        }

        v.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridView.height / ROW_NUMBER)
        return v
    }

    private fun getLabelColor(id: Long) = if (DateUtils.isToday(id)) R.color.colorAccent else R.color.colorPrimaryLight

    internal inner class CalendarHolder {
        var tvDate: TextView? = null
    }

}

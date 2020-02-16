package com.youknow.tabui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.youknow.tabui.R
import com.youknow.tabui.data.model.Item
import kotlinx.android.synthetic.main.item_item.view.*

class ItemsAdapter(
    private val glide: RequestManager,
    val items: MutableList<Item?> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_ITEM -> ItemHolder(parent)
            else -> DividerHolder(parent)
        }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when {
        items[position] != null -> VIEW_TYPE_ITEM
        else -> VIEW_TYPE_DIVIDER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ItemHolder && item != null) {
            bindItem(holder, item)
        }
    }

    private fun bindItem(holder: ItemHolder, item: Item) {
        with (holder) {
            glide.load(item.img).into(ivItem)
            tvName.text = item.name
        }
    }

    fun firstPosition(tag: Any?): Int {
        val position = items.indexOfFirst { it?.category == tag }
        return if (position == -1) {
            0
        } else {
            position
        }
    }

    class ItemHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
    ) {
        val ivItem: ImageView = itemView.ivItem
        val tvName: TextView = itemView.tvName
    }

    class DividerHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_divider, parent, false)
    )

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_DIVIDER = -1
    }
}
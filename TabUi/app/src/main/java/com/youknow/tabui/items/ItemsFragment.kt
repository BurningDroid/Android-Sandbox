package com.youknow.tabui.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.youknow.tabui.R
import com.youknow.tabui.data.model.Item
import kotlinx.android.synthetic.main.fragment_items.*


class ItemsFragment : Fragment(), ItemsContract.View {

    private val presenter: ItemsContract.Presenter by lazy { ItemsPresenter(this) }
    private val itemsAdapter: ItemsAdapter by lazy { ItemsAdapter(Glide.with(activity?.applicationContext!!)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getItemCategoryList()

        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        itemsAdapter.items.clear()
        itemsAdapter.notifyDataSetChanged()
    }

    override fun onCategoryListLoaded(categoryList: List<String>) {
        categoryList.map { category ->
            tabLayout.addTab(tabLayout.newTab().setText(category))
            presenter.getItemsByCategory(category)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onItemsLoaded(items: List<Item>) {
        if (itemsAdapter.items.isNotEmpty()) {
            itemsAdapter.items.add(null)
        }

        itemsAdapter.items.addAll(items)
        itemsAdapter.notifyDataSetChanged()

        Log.d("TEST", "[tab] onItemsLoaded - ${itemsAdapter.items.size}")
    }

    private fun initView() {
        rvStickers.layoutManager = GridLayoutManager(context, 4).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when {
                    itemsAdapter.items[position] == null -> 4
                    else -> 1
                }
            }
        }
        rvStickers.adapter = itemsAdapter
    }
}

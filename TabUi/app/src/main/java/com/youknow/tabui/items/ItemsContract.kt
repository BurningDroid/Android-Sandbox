package com.youknow.tabui.items

import com.youknow.tabui.data.model.Item

interface ItemsContract {
    interface View {
        fun onCategoryListLoaded(categoryList: List<String>)
        fun onItemsLoaded(items: List<Item>)
    }

    interface Presenter {
        fun getItemCategoryList()
        fun getItemsByCategory(category: String)
    }
}
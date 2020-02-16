package com.youknow.tabui.items

import android.util.Log
import com.youknow.tabui.R
import com.youknow.tabui.data.model.Item

class ItemsPresenter(
    private val view: ItemsContract.View
) : ItemsContract.Presenter {

    init {
        Log.d("TEST", "[tab] ItemsPresenter - construct")
    }

    override fun getItemCategoryList() {
        view.onCategoryListLoaded(
            listOf(
                CATEGORY_BASIC,
                CATEGORY_SEASONAL,
                CATEGORY_SITUATION,
                CATEGORY_BRAND
            )
        )
    }

    override fun getItemsByCategory(category: String) {
        val items = when (category) {
            CATEGORY_BASIC -> listOf(
                Item("Basic 1"),
                Item("Basic 2"),
                Item("Basic 3"),
                Item("Basic 4")
            )
            CATEGORY_SEASONAL -> listOf(
                Item("Seasonal 1", R.drawable.ic_item_android_2),
                Item("Seasonal 2", R.drawable.ic_item_android_2),
                Item("Seasonal 3", R.drawable.ic_item_android_2),
                Item("Seasonal 4", R.drawable.ic_item_android_2),
                Item("Seasonal 5", R.drawable.ic_item_android_2),
                Item("Seasonal 6", R.drawable.ic_item_android_2),
                Item("Seasonal 7", R.drawable.ic_item_android_2),
                Item("Seasonal 8", R.drawable.ic_item_android_2)
            )
            CATEGORY_SITUATION -> listOf(
                Item("Situation 1", R.drawable.ic_item_android_3),
                Item("Situation 2", R.drawable.ic_item_android_3),
                Item("Situation 3", R.drawable.ic_item_android_3),
                Item("Situation 4", R.drawable.ic_item_android_3)
            )
            CATEGORY_BRAND -> listOf(
                Item("Brand 1", R.drawable.ic_item_android_4),
                Item("Brand 2", R.drawable.ic_item_android_4),
                Item("Brand 3", R.drawable.ic_item_android_4),
                Item("Brand 4", R.drawable.ic_item_android_4),
                Item("Brand 5", R.drawable.ic_item_android_4),
                Item("Brand 6", R.drawable.ic_item_android_4),
                Item("Brand 7", R.drawable.ic_item_android_4),
                Item("Brand 8", R.drawable.ic_item_android_4)
            )
            else -> null
        }

        if (items != null) {
            view.onItemsLoaded(items)
        }
    }

    companion object {
        private const val CATEGORY_BASIC = "Basic"
        private const val CATEGORY_SEASONAL = "Seasonal"
        private const val CATEGORY_SITUATION = "Situation"
        private const val CATEGORY_BRAND = "Brand"
    }

}
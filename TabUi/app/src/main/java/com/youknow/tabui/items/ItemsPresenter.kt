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
                Item("Basic 1", CATEGORY_BASIC),
                Item("Basic 2", CATEGORY_BASIC),
                Item("Basic 3", CATEGORY_BASIC),
                Item("Basic 4", CATEGORY_BASIC)
            )
            CATEGORY_SEASONAL -> listOf(
                Item("Seasonal 1", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 2", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 3", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 4", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 5", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 6", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 7", CATEGORY_SEASONAL, R.drawable.ic_item_android_2),
                Item("Seasonal 8", CATEGORY_SEASONAL, R.drawable.ic_item_android_2)
            )
            CATEGORY_SITUATION -> listOf(
                Item("Situation 1", CATEGORY_SITUATION, R.drawable.ic_item_android_3),
                Item("Situation 2", CATEGORY_SITUATION, R.drawable.ic_item_android_3),
                Item("Situation 3", CATEGORY_SITUATION, R.drawable.ic_item_android_3),
                Item("Situation 4", CATEGORY_SITUATION, R.drawable.ic_item_android_3)
            )
            CATEGORY_BRAND -> listOf(
                Item("Brand 1", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 2", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 3", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 4", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 5", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 6", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 7", CATEGORY_BRAND, R.drawable.ic_item_android_4),
                Item("Brand 8", CATEGORY_BRAND, R.drawable.ic_item_android_4)
            )
            else -> null
        }

        if (items != null) {
            view.onItemsLoaded(items)
        }
    }

    companion object {
        const val CATEGORY_BASIC = "Basic"
        const val CATEGORY_SEASONAL = "Seasonal"
        const val CATEGORY_SITUATION = "Situation"
        const val CATEGORY_BRAND = "Brand"
    }

}
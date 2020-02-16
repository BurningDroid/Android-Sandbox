package com.youknow.tabui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isShowStickers = false

    private val itemsFragment: ItemsFragment by lazy { ItemsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            showItems()
        }
    }

    private fun showItems() {
        supportFragmentManager.beginTransaction().run {
            when (isShowStickers) {
                true -> remove(itemsFragment)
                else -> add(R.id.stickersContainer, itemsFragment)
            }
        }.commit()
        isShowStickers = !isShowStickers
    }
}

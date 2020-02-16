package com.youknow.tabui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_items.*


class ItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addTab(tabLayout.newTab().setText("Basic"))
        tabLayout.addTab(tabLayout.newTab().setText("Seasonal"))
        tabLayout.addTab(tabLayout.newTab().setText("Situation"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand"))
    }
}

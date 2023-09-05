package com.ahmed.a.habib.werdapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(
    private val list: List<Fragment>,
    fragmentActivity: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentActivity, lifecycle) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
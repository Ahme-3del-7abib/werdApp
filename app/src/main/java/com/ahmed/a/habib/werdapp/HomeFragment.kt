package com.ahmed.a.habib.werdapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.ahmed.a.habib.werdapp.databinding.FragmentHomeBinding
import com.ahmed.a.habib.werdapp.features.customWerd.ui.CustomWerdFragment
import com.ahmed.a.habib.werdapp.features.fixedWerd.ui.FixedWerdFragment
import com.ahmed.a.habib.werdapp.utils.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        setUpViewPager()
    }

    private fun observer() {
        sharedViewModel.counterFragmentIsOpen.observe(viewLifecycleOwner) {
            binding.tabViewPager.isUserInputEnabled = !it
        }

        sharedViewModel.backViewPager.observe(viewLifecycleOwner) {
            onBackPress()
        }
    }

    private fun setUpViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            FixedWerdFragment(),
            CustomWerdFragment()
        )

        val adapter = TabAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.tabViewPager.adapter = adapter
        binding.tabViewPager.offscreenPageLimit = 2

        val pagerTabs = listOf(getString(R.string.fixed_werds), getString(R.string.create_werd))

        TabLayoutMediator(
            binding.mainTabs, binding.tabViewPager
        ) { tab, position ->
            tab.text = pagerTabs[position]
        }.attach()

        binding.tabViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sharedViewModel.setCurrentVPItem(position)
            }
        })
    }

    private fun onBackPress() {
        binding.tabViewPager.setCurrentItem(binding.tabViewPager.currentItem - 1, false)
    }
}
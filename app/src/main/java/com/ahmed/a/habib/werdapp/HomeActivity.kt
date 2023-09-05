package com.ahmed.a.habib.werdapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ahmed.a.habib.werdapp.databinding.ActivityHomeBinding
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.fontPath
import com.ahmed.a.habib.werdapp.utils.addOnBackPressedDispatcher
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var settingsItem: MenuItem? = null
    private lateinit var binding: ActivityHomeBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        init()
        observer()
        handleBackStack()
    }

    private fun observer() {
        sharedViewModel.counterFragmentIsOpen.observe(this) {
            if (it) {
                hideToolAppBar()
            } else {
                showToolAppBar()
            }
        }

        sharedViewModel.getCurrentVPPosition.observe(this) {
            settingsItem?.isVisible = sharedViewModel.getCurrentVPPosition.value != 1
        }
    }

    private fun init() {
        setSupportActionBar(binding.toolBar)
        setTypeFace(listOf(binding.title), font = fontPath)
    }

    private fun handleBackStack() {

        addOnBackPressedDispatcher {
            val navGraph = findNavController(binding.navView.id)

            if (navGraph.currentDestination?.id == R.id.counterFragment) {
                findNavController(binding.navView.id).popBackStack()

            } else if (navGraph.currentDestination?.id == R.id.homeFragment) {

                if (sharedViewModel.getCurrentVPPosition.value != 0) {
                    sharedViewModel.backVPager()
                } else {
                    finish()
                }
            }
        }
    }

    private fun hideToolAppBar() {
        binding.appbar.visibility = GONE
    }

    private fun showToolAppBar() {
        binding.appbar.visibility = VISIBLE
    }
}

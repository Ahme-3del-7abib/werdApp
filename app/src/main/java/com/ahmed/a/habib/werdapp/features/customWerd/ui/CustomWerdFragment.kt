package com.ahmed.a.habib.werdapp.features.customWerd.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmed.a.habib.werdapp.SharedViewModel
import com.ahmed.a.habib.werdapp.databinding.FragmentCustomWerdBinding
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdIntents
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewModel
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewStates
import com.ahmed.a.habib.werdapp.features.fixedWerd.WerdRecyclerAdapter
import com.ahmed.a.habib.werdapp.utils.BaseFragment
import com.ahmed.a.habib.werdapp.utils.PublicKeys
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomWerdFragment :
    BaseFragment<FragmentCustomWerdBinding>(FragmentCustomWerdBinding::inflate) {

    private val viewModel: CustomWerdViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter by lazy { WerdRecyclerAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        render()
        setupRV()
        clickListeners()
    }

    private fun init() {
        requireContext().setTypeFace(
            listOf(
                binding.addWerdBtn, binding.emptyID
            ), font = PublicKeys.fontPath
        )

        viewModel.sendIntend(CustomWerdIntents.GetAllWerds)
    }

    private fun clickListeners() {
        binding.addWerdBtn.setOnClickListener {
            AddWerdBottomSheetFragment(null).show(requireActivity().supportFragmentManager, "TAG")
        }

        adapter.listener = { _, item, _ ->
            EditStartBottomSheetFragment(item).show(requireActivity().supportFragmentManager, "TAG")
        }
    }

    private fun render() {
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is CustomWerdViewStates.ErrorMsg -> {
                    requireContext().showToast(it.msg)
                }

                is CustomWerdViewStates.ErrorId -> {
                    requireContext().showToast(getString(it.id))
                }

                is CustomWerdViewStates.GetAllCustomWerds -> {
                    lifecycleScope.launch {
                        it.werdsList.collectLatest { list ->
                            if (list.isEmpty()) {
                                showEmptyView()
                            } else {
                                hideEmptyView()
                                adapter.submitList(list)
                            }
                        }
                    }
                }

                else -> {
                    /* NO action needed */
                }
            }
        }
    }

    private fun showEmptyView() {
        binding.rv.visibility = GONE
        binding.emptyID.visibility = VISIBLE
    }

    private fun hideEmptyView() {
        binding.rv.visibility = VISIBLE
        binding.emptyID.visibility = GONE
    }

    private fun setupRV() {
        binding.rv.layoutManager =
            GridLayoutManager(
                requireContext(), 2,
                GridLayoutManager.VERTICAL,
                false
            )

        binding.rv.adapter = adapter
    }
}
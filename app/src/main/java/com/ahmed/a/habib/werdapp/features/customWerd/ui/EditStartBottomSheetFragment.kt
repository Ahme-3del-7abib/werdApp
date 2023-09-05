package com.ahmed.a.habib.werdapp.features.customWerd.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.HomeFragmentDirections
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.databinding.FragmentEditStartBottomSheetBinding
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdIntents
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewModel
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewStates
import com.ahmed.a.habib.werdapp.utils.BaseBottomSheetFragment
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.fontPath
import com.ahmed.a.habib.werdapp.utils.expandWrapBottomSheet
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditStartBottomSheetFragment(private val item: WerdDataItems?) :
    BaseBottomSheetFragment<FragmentEditStartBottomSheetBinding>(FragmentEditStartBottomSheetBinding::inflate) {

    private val viewModel: CustomWerdViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        render()
        clickListeners()
    }

    private fun init() {
        requireContext().setTypeFace(
            listOf(binding.startBtn, binding.editBtn, binding.deleteBtn),
            font = fontPath
        )

        requireContext().expandWrapBottomSheet(dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun render() = lifecycleScope.launch {
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is CustomWerdViewStates.SUCCESS -> {
                    dismiss()
                }

                is CustomWerdViewStates.ErrorMsg -> {
                    requireContext().showToast(it.msg)
                }

                is CustomWerdViewStates.ErrorId -> {
                    requireContext().showToast(getString(it.id))
                }

                else -> {
                    /* NO Action needed */
                }
            }
        }
    }

    private fun clickListeners() {
        binding.startBtn.setOnClickListener {
            dismiss()
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCounterFragment(
                    item?._ID ?: -1
                )
            )
        }

        binding.editBtn.setOnClickListener {
            dismiss()

            AddWerdBottomSheetFragment(item).show(
                requireActivity().supportFragmentManager,
                "TAG"
            )
        }

        binding.deleteBtn.setOnClickListener {
            if (item != null) {
                viewModel.sendIntend(CustomWerdIntents.Delete(id = item._ID))
            }
        }
    }
}
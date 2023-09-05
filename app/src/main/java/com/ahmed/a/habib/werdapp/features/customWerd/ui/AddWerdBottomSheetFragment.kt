package com.ahmed.a.habib.werdapp.features.customWerd.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.databinding.FragmentAddWerdBottomSheetBinding
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdIntents
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewModel
import com.ahmed.a.habib.werdapp.features.customWerd.CustomWerdViewStates
import com.ahmed.a.habib.werdapp.utils.BaseBottomSheetFragment
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.fontPath
import com.ahmed.a.habib.werdapp.utils.expandWrapBottomSheet
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWerdBottomSheetFragment(private val item: WerdDataItems?) :
    BaseBottomSheetFragment<FragmentAddWerdBottomSheetBinding>(FragmentAddWerdBottomSheetBinding::inflate) {

    private val viewModel: CustomWerdViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        render()
        onClickListeners()
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

                is CustomWerdViewStates.SUCCESS -> {
                    dismiss()
                }

                CustomWerdViewStates.InputIsValid -> {
                    if (item == null) {
                        save()
                    } else {
                        update(item._ID)
                    }
                }

                else -> {
                    /* NO ACTION NEEDED */
                }
            }
        }
    }

    private fun save() {
        viewModel.sendIntend(
            CustomWerdIntents.Insert(
                WerdDataItems(
                    werdName = getWerdName(),
                    werdContent = getWerdContent(),
                    werdMaxCount = getWerdQadr()
                )
            )
        )
    }

    private fun update(id: Int?) {
        if (item != null) {
            viewModel.sendIntend(
                CustomWerdIntents.Update(
                    WerdDataItems(
                        _ID = id,
                        werdName = getWerdName(),
                        werdContent = getWerdContent(),
                        werdCurrentCount = item.werdCurrentCount,
                        werdMaxCount = getWerdQadr()
                    )
                )
            )
        }
    }

    private fun init() {
        if (item != null) {
            binding.nameED.setText(item.werdName)
            binding.contentED.setText(item.werdContent)
            binding.countED.setText(item.werdMaxCount.toInt().toString())
            binding.saveBtn.text = getString(R.string.edit)
        }

        requireContext().setTypeFace(
            listOf(binding.saveBtn), font = fontPath
        )

        requireContext().expandWrapBottomSheet(dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun onClickListeners() {
        binding.saveBtn.setOnClickListener {
            viewModel.sendIntend(
                CustomWerdIntents.ValidateInput(
                    binding.nameED,
                    binding.contentED,
                    binding.countED,
                    item?.werdCurrentCount
                )
            )
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun getWerdName(): String {
        return binding.nameED.text.toString()
    }

    private fun getWerdContent(): String {
        return binding.contentED.text.toString()
    }

    private fun getWerdQadr(): Float {
        return binding.countED.text.toString().toFloat()
    }
}
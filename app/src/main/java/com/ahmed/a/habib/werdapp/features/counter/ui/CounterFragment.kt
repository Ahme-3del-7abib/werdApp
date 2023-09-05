package com.ahmed.a.habib.werdapp.features.counter.ui

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.SharedViewModel
import com.ahmed.a.habib.werdapp.databinding.FragmentCounterBinding
import com.ahmed.a.habib.werdapp.features.counter.CounterIntents
import com.ahmed.a.habib.werdapp.features.counter.CounterViewModel
import com.ahmed.a.habib.werdapp.features.counter.CounterViewStates
import com.ahmed.a.habib.werdapp.utils.BaseFragment
import com.ahmed.a.habib.werdapp.utils.InputDialogActions
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.fontPath
import com.ahmed.a.habib.werdapp.utils.inputUserDialog
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.showAlertDialogWithOkAction
import com.ahmed.a.habib.werdapp.utils.showToast
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CounterFragment : BaseFragment<FragmentCounterBinding>(FragmentCounterBinding::inflate) {

    private var maxCount = 0f
    private var currentCount: Float = 0f

    private val navArgs: CounterFragmentArgs by navArgs()
    private val viewModel: CounterViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        render()
        setClickListeners()
    }

    private fun init() {
        requireContext().setTypeFace(
            listOf(
                binding.segaTV, binding.resetCountBtn, binding.changeMaxCountBtn
            ), fontPath
        )

        binding.circularProgressBar.apply {

            progressBarColorEnd = ContextCompat.getColor(requireContext(), R.color.green_i)
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            backgroundProgressBarColorStart = Color.WHITE

            backgroundProgressBarColorEnd =
                ContextCompat.getColor(requireContext(), R.color.green_i)

            backgroundProgressBarColorDirection =
                CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            progressBarWidth = 12f
            backgroundProgressBarWidth = 4f

            startAngle = 180f
            roundBorder = true
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

        viewModel.sendIntend(CounterIntents.GetWerdById(navArgs.werdId))
    }

    private fun setClickListeners() {
        binding.resetCountBtn.setOnClickListener { reset() }

        binding.changeMaxCountBtn.setOnClickListener { updateMaxCount() }

        binding.circularProgressBar.setOnClickListener { setProgress(++currentCount) }
    }

    private fun render() {
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is CounterViewStates.ErrorMsg -> requireContext().showToast(it.msg)

                is CounterViewStates.ErrorId -> requireContext().showToast(getString(it.id))

                is CounterViewStates.GotWerd -> setReturnedDataIntoView(it.werd)

                is CounterViewStates.InputIsValid -> {
                    setMaxCount(it.maxCount)
                    viewModel.sendIntend(CounterIntents.UpdateMaxCount(it.maxCount, navArgs.werdId))
                }

                else -> {
                    /* No action needed */
                }
            }
        }
    }

    private fun setReturnedDataIntoView(werd: WerdDataItems) {
        maxCount = werd.werdMaxCount

        setMaxCount(maxCount)
        setNewCount(werd.werdCurrentCount)

        binding.segaTV.text = werd.werdContent

        if (werd.isFixedWerd) {
            binding.changeMaxCountBtn.visibility = View.VISIBLE
        }
    }

    private fun setProgress(number: Float) {
        if (number > maxCount) {
            reset()
            binding.circularProgressBar.isEnabled = false
        } else {
            setNewCount(number)
        }
    }

    private fun reset() {
        showAlertDialogWithOkAction(
            requireActivity(),
            getString(R.string.app_name),
            getString(R.string.start_new_werd),
            getString(R.string._ok)
        ) {
            setProgress(0f)
            currentCount = 0f
            binding.circularProgressBar.isEnabled = true
        }
    }

    private fun updateMaxCount() {
        requireActivity().inputUserDialog(getString(R.string.enter_werd_count),
            object : InputDialogActions {

                override fun onCancelBtnClicked(dialog: DialogInterface) {
                    dialog.dismiss()
                }

                override fun onOkBtnClicked(input: String, dialog: DialogInterface) {
                    dialog.dismiss()
                    viewModel.sendIntend(
                        CounterIntents.ValidateInput(
                            currentCount = currentCount,
                            maxCount = input
                        )
                    )
                }
            })
    }

    private fun setNewCount(newCount: Float) {
        currentCount = newCount
        binding.countNumTV.text = currentCount.toInt().toString()
        binding.circularProgressBar.setProgressWithAnimation(currentCount, 400)
    }

    private fun setMaxCount(mCount: Float) {
        maxCount = mCount
        binding.maxCountTV.text = maxCount.toInt().toString()
        binding.circularProgressBar.progressMax = maxCount
    }

    override fun onStart() {
        super.onStart()
        sharedViewModel.setCounterFragmentIsOpen(true)
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.setCounterFragmentIsOpen(false)
        viewModel.sendIntend(CounterIntents.UpdateCurrentCount(currentCount, navArgs.werdId))
    }
}
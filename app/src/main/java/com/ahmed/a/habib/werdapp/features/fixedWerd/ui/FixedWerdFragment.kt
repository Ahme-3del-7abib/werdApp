package com.ahmed.a.habib.werdapp.features.fixedWerd.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.HomeFragmentDirections
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.databinding.FragmentFixedWerdBinding
import com.ahmed.a.habib.werdapp.features.fixedWerd.FixedWerdIntents
import com.ahmed.a.habib.werdapp.features.fixedWerd.FixedWerdViewModel
import com.ahmed.a.habib.werdapp.features.fixedWerd.FixedWerdViewStates
import com.ahmed.a.habib.werdapp.features.fixedWerd.WerdRecyclerAdapter
import com.ahmed.a.habib.werdapp.utils.BaseFragment
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.baqFlag
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.ekhlasFlag
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.estFlag
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.salahFlag
import com.ahmed.a.habib.werdapp.utils.bitMapToString
import com.ahmed.a.habib.werdapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixedWerdFragment :
    BaseFragment<FragmentFixedWerdBinding>(FragmentFixedWerdBinding::inflate) {

    private val viewModel: FixedWerdViewModel by viewModels()

    private val adapter by lazy { WerdRecyclerAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        render()
        setupRV()
        setClickListeners()
    }

    private fun init() {
        viewModel.sendIntend(FixedWerdIntents.GetAllFixedWerds)
    }

    private fun setClickListeners() {
        adapter.listener = { _, item, _ ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCounterFragment(item._ID ?: -1)
            )
        }
    }

    private fun render() {
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is FixedWerdViewStates.ERROR -> {
                    requireContext().showToast(it.error)
                }

                is FixedWerdViewStates.SuccessGetAllWerds -> {
                    if (it.werdList.isEmpty()) {
                        viewModel.sendIntend(
                            FixedWerdIntents.InsertFixedWerd(
                                listOf(
                                    WerdDataItems(
                                        _ID = 0,
                                        werdName = getString(R.string.napi_werd),
                                        werdContent = getString(R.string.salah_content),
                                        werdMaxCount = 100f,
                                        werdCurrentCount = 0f,
                                        werdIcon = getIcon(salahFlag)
                                    ),
                                    WerdDataItems(
                                        _ID = 1,
                                        werdName = getString(R.string.baquat_werd),
                                        werdContent = getString(R.string.al_baqyat_al_salhat),
                                        werdMaxCount = 100f,
                                        werdCurrentCount = 0f,
                                        werdIcon = getIcon(baqFlag)
                                    ),
                                    WerdDataItems(
                                        _ID = 2,
                                        werdName = getString(R.string.estg_werd),
                                        werdContent = getString(R.string.astgfr_allah),
                                        werdMaxCount = 100f,
                                        werdCurrentCount = 0f,
                                        werdIcon = getIcon(estFlag)
                                    ),
                                    WerdDataItems(
                                        _ID = 3,
                                        werdName = getString(R.string.eklas_werd),
                                        werdContent = getString(R.string.ekhlas_sura),
                                        werdMaxCount = 10f,
                                        werdCurrentCount = 0f,
                                        werdIcon = getIcon(ekhlasFlag)
                                    ),
                                )
                            )
                        )
                    } else {
                        adapter.submitList(it.werdList)
                    }
                }

                is FixedWerdViewStates.SuccessInsertFixedWerd -> {
                    viewModel.sendIntend(FixedWerdIntents.GetAllFixedWerds)
                }
            }
        }
    }

    private fun setupRV() {
        binding.fixedWerdRV.layoutManager =
            GridLayoutManager(
                requireContext(), 2,
                GridLayoutManager.VERTICAL,
                false
            )

        binding.fixedWerdRV.adapter = adapter
    }

    private fun getIcon(flag: String): String? {
        when (flag) {
            salahFlag -> {
                return BitmapFactory
                    .decodeResource(resources, R.drawable.ic_circle_mushaf)
                    .bitMapToString()
            }

            estFlag -> {
                return BitmapFactory
                    .decodeResource(resources, R.drawable.ic_sebha)
                    .bitMapToString()
            }

            baqFlag -> {
                return BitmapFactory
                    .decodeResource(resources, R.drawable.ic_mushaf)
                    .bitMapToString()
            }

            ekhlasFlag -> {
                return BitmapFactory
                    .decodeResource(resources, R.drawable.ic_book)
                    .bitMapToString()
            }

            else -> {
                return null
            }
        }
    }
}
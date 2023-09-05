package com.ahmed.a.habib.werdapp.features.fixedWerd

import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems

sealed class FixedWerdViewStates {

    data class ERROR(val error: String) : FixedWerdViewStates()

    object SuccessInsertFixedWerd : FixedWerdViewStates()

    data class SuccessGetAllWerds(val werdList: List<WerdDataItems>) : FixedWerdViewStates()
}
package com.ahmed.a.habib.werdapp.features.fixedWerd

import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems

sealed class FixedWerdIntents {

    data class InsertFixedWerd(val fixedWerdList: List<WerdDataItems>) : FixedWerdIntents()

    object GetAllFixedWerds : FixedWerdIntents()
}
package com.ahmed.a.habib.werdapp.features.customWerd

import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import kotlinx.coroutines.flow.Flow

sealed class CustomWerdViewStates {

    data class ErrorMsg(val msg: String) : CustomWerdViewStates()

    data class ErrorId(val id: Int) : CustomWerdViewStates()

    object SUCCESS : CustomWerdViewStates()

    object InputIsValid : CustomWerdViewStates()

    data class GetAllCustomWerds(
        val werdsList: Flow<List<WerdDataItems>>
    ) : CustomWerdViewStates()
}
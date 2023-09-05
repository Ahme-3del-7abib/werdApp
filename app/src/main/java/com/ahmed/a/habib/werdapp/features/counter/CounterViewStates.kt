package com.ahmed.a.habib.werdapp.features.counter

import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems

sealed class CounterViewStates {

    object Success : CounterViewStates()

    data class InputIsValid(val maxCount: Float) : CounterViewStates()

    data class ErrorMsg(val msg: String) : CounterViewStates()

    data class ErrorId(val id: Int) : CounterViewStates()

    data class GotWerd(val werd: WerdDataItems) : CounterViewStates()
}

package com.ahmed.a.habib.werdapp.features.counter

sealed class CounterIntents {

    data class UpdateCurrentCount(val count: Float, val id: Int) : CounterIntents()

    data class ValidateInput(val currentCount: Float, val maxCount: String) : CounterIntents()

    data class UpdateMaxCount(val count: Float, val id: Int) : CounterIntents()

    data class GetWerdById(val id: Int) : CounterIntents()
}

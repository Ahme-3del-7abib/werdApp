package com.ahmed.a.habib.data.counter.repo

import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

interface CounterRepo {

    suspend fun updateCurrentCount(count: Float, id: Int)

    suspend fun updateMaxCount(count: Float, id: Int)

    fun getWerdById(id: Int): WerdModel
}
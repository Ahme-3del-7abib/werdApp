package com.ahmed.a.habib.domain.counter.useCases

import com.ahmed.a.habib.data.counter.repo.CounterRepo
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.domain.fixedWerd.model.toDataItem
import javax.inject.Inject


class CounterUseCases @Inject constructor(private val repo: CounterRepo) {

    suspend fun updateCurrentCount(count: Float, id: Int) {
        repo.updateCurrentCount(count, id)
    }

    suspend fun updateMaxCount(count: Float, id: Int) {
        repo.updateMaxCount(count, id)
    }

    fun getWerdById(id: Int): WerdDataItems = repo.getWerdById(id).toDataItem()
}
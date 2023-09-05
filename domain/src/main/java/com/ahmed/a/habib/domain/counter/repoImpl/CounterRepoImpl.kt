package com.ahmed.a.habib.domain.counter.repoImpl

import com.ahmed.a.habib.data.counter.local.CounterDao
import com.ahmed.a.habib.data.counter.repo.CounterRepo
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import javax.inject.Inject

class CounterRepoImpl @Inject constructor(private val dao: CounterDao) : CounterRepo {

    override suspend fun updateCurrentCount(count: Float, id: Int) {
        dao.updateCurrentCount(count, id)
    }

    override suspend fun updateMaxCount(count: Float, id: Int) {
        dao.updateMaxCount(count, id)
    }

    override fun getWerdById(id: Int): WerdModel = dao.getWerdyId(id)
}
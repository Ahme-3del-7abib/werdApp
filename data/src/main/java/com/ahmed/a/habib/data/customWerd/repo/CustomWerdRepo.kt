package com.ahmed.a.habib.data.customWerd.repo

import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import kotlinx.coroutines.flow.Flow

interface CustomWerdRepo {

    suspend fun insert(werd: WerdModel)

    suspend fun update(werd: WerdModel)

    suspend fun delete(id: Int)

    fun getAllWerds(): Flow<List<WerdModel>>
}
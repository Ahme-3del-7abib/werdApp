package com.ahmed.a.habib.data.fixedWerd.repo

import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

interface FixedWerdRepo {

    suspend fun insert(werd: WerdModel)

    fun getAllWerds(): List<WerdModel>
}
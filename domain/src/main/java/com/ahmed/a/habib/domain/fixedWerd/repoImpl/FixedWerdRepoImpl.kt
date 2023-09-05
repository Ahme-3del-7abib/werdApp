package com.ahmed.a.habib.domain.fixedWerd.repoImpl

import com.ahmed.a.habib.data.fixedWerd.local.FixedWerdDao
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import com.ahmed.a.habib.data.fixedWerd.repo.FixedWerdRepo
import javax.inject.Inject

class FixedWerdRepoImpl @Inject constructor(private val fixedWerdDao: FixedWerdDao) : FixedWerdRepo {

    override suspend fun insert(werd: WerdModel) {
        fixedWerdDao.insert(werd)
    }

    override fun getAllWerds(): List<WerdModel> = fixedWerdDao.getAllWerds()
}
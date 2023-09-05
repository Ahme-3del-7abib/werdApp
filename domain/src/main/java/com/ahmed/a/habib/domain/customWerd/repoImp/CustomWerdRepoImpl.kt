package com.ahmed.a.habib.domain.customWerd.repoImp

import com.ahmed.a.habib.data.customWerd.local.CustomWerdDao
import com.ahmed.a.habib.data.customWerd.repo.CustomWerdRepo
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomWerdRepoImpl @Inject constructor(val customWerdDao: CustomWerdDao) : CustomWerdRepo {

    override suspend fun insert(werd: WerdModel) {
        customWerdDao.insert(werd)
    }

    override suspend fun update(werd: WerdModel) {
        customWerdDao.update(
            werd._ID,
            werd.werdName,
            werd.werdContent,
            werd.werdMaxCount,
            werd.werdCurrentCount
        )
    }

    override suspend fun delete(id: Int) {
        customWerdDao.delete(id)
    }

    override fun getAllWerds(): Flow<List<WerdModel>> {
        return customWerdDao.getAllWerds()
    }
}
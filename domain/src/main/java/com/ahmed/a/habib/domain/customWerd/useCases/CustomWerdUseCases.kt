package com.ahmed.a.habib.domain.customWerd.useCases

import com.ahmed.a.habib.data.customWerd.repo.CustomWerdRepo
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.domain.fixedWerd.model.toDataItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomWerdUseCases @Inject constructor(private val customWerdRepo: CustomWerdRepo) {

    suspend fun insertCustomWerd(werd: WerdDataItems) {
        customWerdRepo.insert(
            WerdModel(
                _ID = werd._ID,
                isFixedWerd = false,
                werdName = werd.werdName,
                werdContent = werd.werdContent,
                werdMaxCount = werd.werdMaxCount,
                werdCurrentCount = werd.werdCurrentCount,
                werdIcon = werd.werdIcon
            )
        )
    }

    suspend fun updateCustomWerd(werd: WerdDataItems) {
        customWerdRepo.update(
            WerdModel(
                _ID = werd._ID,
                werdName = werd.werdName,
                werdContent = werd.werdContent,
                werdMaxCount = werd.werdMaxCount,
                werdCurrentCount = werd.werdCurrentCount,
                werdIcon = werd.werdIcon
            )
        )
    }

    suspend fun delete(id: Int) {
        customWerdRepo.delete(id)
    }

    fun getAllCustomWerds(): Flow<List<WerdDataItems>> =
        customWerdRepo.getAllWerds().map { it.toDataItems() }

}
package com.ahmed.a.habib.domain.fixedWerd.useCases

import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import com.ahmed.a.habib.data.fixedWerd.repo.FixedWerdRepo
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.domain.fixedWerd.model.toDataItems
import javax.inject.Inject

class FixedWerdUseCases @Inject constructor(private val repo: FixedWerdRepo) {

    suspend fun insertFixedWerd(werd: WerdDataItems) {
        repo.insert(
            WerdModel(
                _ID = werd._ID,
                isFixedWerd = true,
                werdName = werd.werdName,
                werdContent = werd.werdContent,
                werdMaxCount = werd.werdMaxCount,
                werdCurrentCount = werd.werdCurrentCount,
                werdIcon = werd.werdIcon
            )
        )
    }

    fun getAllFixedWerds(): List<WerdDataItems> = repo.getAllWerds().toDataItems()
}
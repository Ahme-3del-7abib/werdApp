package com.ahmed.a.habib.data.counter.local

import androidx.room.Dao
import androidx.room.Query
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

@Dao
interface CounterDao {

    @Query("UPDATE werd_table SET werdCurrentCount=:count WHERE ID LIKE :id")
    suspend fun updateCurrentCount(count: Float, id: Int)

    @Query("UPDATE werd_table SET werdMaxCount=:count WHERE ID LIKE :id")
    suspend fun updateMaxCount(count: Float, id: Int)

    @Query("SELECT * FROM werd_table WHERE ID LIKE :id")
    fun getWerdyId(id: Int): WerdModel
}
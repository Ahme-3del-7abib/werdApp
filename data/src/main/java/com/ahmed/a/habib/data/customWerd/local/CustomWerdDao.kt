package com.ahmed.a.habib.data.customWerd.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomWerdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(werd: WerdModel)

    @Query("UPDATE werd_table SET werdName=:name,werdMaxCount=:maxCount,werdContent=:content,werdCurrentCount=:currentCount WHERE ID LIKE :id")
    suspend fun update(
        id: Int?,
        name: String,
        content: String,
        maxCount: Float,
        currentCount: Float
    )

    @Query("DELETE FROM werd_table WHERE ID = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM werd_table WHERE isFixedWerd=0")
    fun getAllWerds(): Flow<List<WerdModel>>
}
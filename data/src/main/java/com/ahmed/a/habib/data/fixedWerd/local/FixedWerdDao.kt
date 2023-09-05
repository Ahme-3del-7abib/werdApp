package com.ahmed.a.habib.data.fixedWerd.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

@Dao
interface FixedWerdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(werd: WerdModel)

    @Query("SELECT * FROM werd_table WHERE isFixedWerd=1")
    fun getAllWerds(): List<WerdModel>
}
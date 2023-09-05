package com.ahmed.a.habib.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed.a.habib.data.counter.local.CounterDao
import com.ahmed.a.habib.data.customWerd.local.CustomWerdDao
import com.ahmed.a.habib.data.fixedWerd.local.FixedWerdDao
import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

@Database(
    entities = [WerdModel::class],
    version = 1,
    exportSchema = false
)
abstract class WerdDataBase : RoomDatabase() {

    abstract fun fixedWerdDao(): FixedWerdDao

    abstract fun customWerdDao(): CustomWerdDao

    abstract fun counterDao(): CounterDao
}
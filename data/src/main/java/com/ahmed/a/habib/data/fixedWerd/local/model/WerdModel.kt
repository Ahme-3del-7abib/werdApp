package com.ahmed.a.habib.data.fixedWerd.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "werd_table")
data class WerdModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val _ID: Int? = null,

    @ColumnInfo(name = "isFixedWerd")
    val isFixedWerd: Boolean = false,

    @ColumnInfo(name = "werdName")
    val werdName: String = "",

    @ColumnInfo(name = "werdContent")
    val werdContent: String = "",

    @ColumnInfo(name = "werdMaxCount")
    val werdMaxCount: Float = 0f,

    @ColumnInfo(name = "werdCurrentCount")
    val werdCurrentCount: Float = 0f,

    @ColumnInfo(name = "werdIcon")
    val werdIcon: String? = ""

) : Serializable
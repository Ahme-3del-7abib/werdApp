package com.ahmed.a.habib.domain.fixedWerd.model

import com.ahmed.a.habib.data.fixedWerd.local.model.WerdModel

data class WerdDataItems(
    val _ID: Int? = null,
    val isFixedWerd: Boolean = false,
    val werdName: String = "",
    val werdContent: String = "",
    val werdMaxCount: Float = 0f,
    val werdCurrentCount: Float = 0f,
    val werdIcon: String? = ""
)

fun List<WerdModel>.toDataItems(): List<WerdDataItems> {
    return map { it.toDataItem() }
}

fun WerdModel.toDataItem(): WerdDataItems {
    return WerdDataItems(
        this._ID,
        this.isFixedWerd,
        this.werdName,
        this.werdContent,
        this.werdMaxCount,
        this.werdCurrentCount,
        this.werdIcon
    )
}
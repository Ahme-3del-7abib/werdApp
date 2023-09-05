package com.ahmed.a.habib.werdapp.features.customWerd

import android.widget.EditText
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems

sealed class CustomWerdIntents {

    data class Insert(val werd: WerdDataItems) : CustomWerdIntents()

    data class Update(val werd: WerdDataItems) : CustomWerdIntents()

    data class Delete(val id: Int?) : CustomWerdIntents()

    object GetAllWerds : CustomWerdIntents()

    data class ValidateInput(
        val nameEd: EditText,
        val contentED: EditText,
        val maxCountED: EditText,
        val currentCount: Float?
    ) : CustomWerdIntents()
}
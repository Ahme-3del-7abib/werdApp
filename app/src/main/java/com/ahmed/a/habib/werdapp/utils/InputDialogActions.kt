package com.ahmed.a.habib.werdapp.utils

import android.content.DialogInterface

interface InputDialogActions {

    fun onCancelBtnClicked(dialog: DialogInterface)

    fun onOkBtnClicked(input: String, dialog: DialogInterface)
}

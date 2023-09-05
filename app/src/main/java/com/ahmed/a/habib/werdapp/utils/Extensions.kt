package com.ahmed.a.habib.werdapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.ByteArrayOutputStream


fun View.startUiAnimation(duration: Long) {
    val animation = AlphaAnimation(0.0f, 1.0f)
    animation.duration = duration
    this.startAnimation(animation)
}

fun Context.setTypeFace(view: List<TextView>, font: String) {
    view.forEach {
        it.typeface = Typeface.createFromAsset(this.assets, font)
    }
}

fun getRandom(from: Int, to: Int) = (Math.random() * (to - from + 1) + from).toInt()

fun showAlertDialogWithOkAction(
    activity: Activity,
    title: String,
    message: String,
    buttonName: String,
    function: () -> Unit
) {
    AlertDialog.Builder(activity).setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            buttonName
        ) { _, _ ->
            function.invoke()
        }
        .setCancelable(false)
        .create()
        .show()
}

fun Activity.inputUserDialog(hint: String, inputDialogActions: InputDialogActions) {

    val layoutInflater = LayoutInflater.from(this)
    val promptView: View =
        layoutInflater.inflate(com.ahmed.a.habib.werdapp.R.layout.dialog_user_input, null)
    val alertDialogBuilder = AlertDialog.Builder(this)

    alertDialogBuilder.setView(promptView)

    val ok = promptView.findViewById<Button>(com.ahmed.a.habib.werdapp.R.id.ok)
    val cancel = promptView.findViewById<Button>(com.ahmed.a.habib.werdapp.R.id.cancel)
    val input = promptView.findViewById<EditText>(com.ahmed.a.habib.werdapp.R.id.input)

    input.hint = hint

    val alertDialog = alertDialogBuilder.create()
    alertDialog.setCancelable(false)

    ok.setOnClickListener {
        inputDialogActions.onOkBtnClicked(
            input.text.toString().trim(),
            alertDialog
        )
    }

    cancel.setOnClickListener {
        inputDialogActions.onCancelBtnClicked(
            alertDialog
        )
    }

    alertDialog.show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.expandCustomBottomSheet(dialog: Dialog?, height: Double) {
    dialog?.setOnShowListener {
        val d = it as BottomSheetDialog
        val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val displayMetrics = this.resources.displayMetrics
        val h = displayMetrics.heightPixels
        val maxHeight = (h * height).toInt()
        bottomSheet.layoutParams.height = maxHeight
        bottomSheetBehavior.peekHeight = h
        bottomSheetBehavior.skipCollapsed = false
        coordinatorLayout.parent.requestLayout()
    }
}

fun Context.expandBottomSheet(dialog: Dialog?) {
    dialog?.setOnShowListener {
        val d = it as BottomSheetDialog
        val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val displayMetrics = this.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val maxHeight = (height * 0.85).toInt()
        bottomSheet.layoutParams.height = maxHeight
        bottomSheetBehavior.peekHeight = height
        bottomSheetBehavior.skipCollapsed = false
        coordinatorLayout.parent.requestLayout()
    }
}

fun Context.expandWrapBottomSheet(dialog: Dialog?) {
    dialog?.setOnShowListener {
        val d = it as BottomSheetDialog
        val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val displayMetrics = this.resources.displayMetrics
        val height = displayMetrics.heightPixels
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        bottomSheetBehavior.peekHeight = height
        bottomSheetBehavior.skipCollapsed = false
        coordinatorLayout.parent.requestLayout()
    }
}

fun Context.fullyExtendBottomSheet(dialog: Dialog?) {
    dialog?.setOnShowListener {
        val d = it as BottomSheetDialog
        val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val displayMetrics = this.resources.displayMetrics
        val height = displayMetrics.heightPixels
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheetBehavior.state = STATE_EXPANDED
        bottomSheetBehavior.peekHeight = height
        bottomSheetBehavior.skipCollapsed = false
        coordinatorLayout.parent.requestLayout()
    }
}

fun Context.getFullExpandDialog(theme: Int): Dialog {
    val dialog = BottomSheetDialog(this, theme)
    dialog.setOnShowListener {
        val bottomSheetDialog = it as BottomSheetDialog
        val parentLayout =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        parentLayout?.let { it1 ->
            val behaviour = BottomSheetBehavior.from(it1)
            setupFullHeight(it1)
            behaviour.state = STATE_EXPANDED
        }
    }

    return dialog
}

private fun setupFullHeight(bottomSheet: View) {
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    bottomSheet.layoutParams = layoutParams
}

fun Bitmap.bitMapToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun String.stringToBitMap(): Bitmap? {
    return try {
        val encodeByte = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        null
    }
}

fun AppCompatActivity.addOnBackPressedDispatcher(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed.invoke()
            }
        }
    )
}
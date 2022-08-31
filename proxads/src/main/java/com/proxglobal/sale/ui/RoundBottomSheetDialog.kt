package com.proxglobal.sale.ui

import android.app.Dialog
import android.graphics.Outline
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.afollestad.materialdialogs.utils.MDUtil.updatePadding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.proxglobal.sale.utils.dp


abstract class BaseRoundBottomSheetDialog(
    private val topLeftRadius: Float = 16.dp,
    private val topRightRadius: Float = 16.dp,
    private val bottomLeftRadius: Float = 0.dp,
    private val bottomRightRadius: Float = 0.dp,
    private val leftOffset: Int = 0.dp.toInt(),
    private val rightOffset: Int = 0.dp.toInt(),
    private val bottomOffset: Int = 0.dp.toInt(),
) : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.apply {
            setOnShowListener {
                findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)!!.apply {
                    val d = GradientDrawable().apply {
                        mutate()
                        cornerRadii = floatArrayOf(
                            topLeftRadius, topLeftRadius,
                            topRightRadius, topRightRadius,
                            bottomRightRadius, bottomRightRadius,
                            bottomLeftRadius, bottomLeftRadius
                        )
                    }
                    // set offset to the edge
                    updatePadding(leftOffset, 0, rightOffset, bottomOffset)
                    background = d
                }
            }
            behavior.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.clipToOutline = true
    }
}


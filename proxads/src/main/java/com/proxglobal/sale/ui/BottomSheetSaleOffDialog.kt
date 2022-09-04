package com.proxglobal.sale.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.proxglobal.proxads.R
import com.proxglobal.sale.model.sale.SaleScript

class BottomSheetSaleOffDialog(private val script: SaleScript): BaseRoundBottomSheetDialog() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sale_off_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            findViewById<View>(R.id.bt_exit_pop_up).setOnClickListener {
                dismiss()
            }
            val background = findViewById<ImageView>(R.id.iv_popup_background)
            Glide.with(background)
                .load(script.saleDesign?.background)
                .override(Target.SIZE_ORIGINAL)
                .into(background)
        }
    }
}
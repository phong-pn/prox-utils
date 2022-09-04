package com.proxglobal.sale.ui

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.proxglobal.proxads.R
import com.proxglobal.sale.model.sale.SaleScript
import java.net.URL

class SaleOffDialog(private val script: SaleScript): RoundDialog() {
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
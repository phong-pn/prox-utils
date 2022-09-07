package com.proxglobal.sale.controller.behavior

import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.proxglobal.proxads.R
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript

open class DefaultShowBannerSaleBehavior(private val container: FrameLayout) :
    ConditionOnShowSaleBehavior() {
    override fun onShow(event: SaleEvent, script: SaleScript) {
        super.onShow(event, script)
        val banner =
            LayoutInflater.from(container.context).inflate(R.layout.layout_banner_sale, null)
        container.addView(banner, FrameLayout.LayoutParams(-1, -1)) // -1 is  MATCH_PARENT
        banner.apply {
            val bannerImage = findViewById<ImageView>(R.id.iv_sale_banner)
            Glide.with(bannerImage)
                .load(script.saleDesign!!.image)
                .override(Target.SIZE_ORIGINAL)
                .into(bannerImage)
        }
    }
}
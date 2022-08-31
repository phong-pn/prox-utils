package com.proxglobal.sale.controller

import android.app.Application
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.proxglobal.proxads.R
import com.proxglobal.sale.data.RemoteConfigSource
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript

object ProxSale {
    private val source = RemoteConfigSource()
    private val listenerMap = mutableMapOf<Int, ShowSaleCallback>()

    fun fetch(application: Application) = source.fetch(application)
    private val saleEvent: SaleEvent
        get() = source.event

    fun showSale(
        scriptId: Int,
        listener: ShowSaleCallback,
    ) {
        if (listener != listenerMap[scriptId]) listenerMap[scriptId] = listener
        val script = source.getScript(scriptId)!!
        if (listener.checkCondition(saleEvent, script)) {
            listener.onShow(saleEvent, script)
        } else listener.onCancel(saleEvent, script)
    }

    fun showBanner(container: FrameLayout, scriptId: Int) {
        val banner =
            LayoutInflater.from(container.context).inflate(R.layout.layout_banner_sale, null)
        container.addView(banner, FrameLayout.LayoutParams(-1, -1)) // -1 is  MATCH_PARENT
    }
}

interface ShowSaleCallback {
    /**
     * Call when sale condition is not match
     */
    fun onCancel(event: SaleEvent, script: SaleScript) {}

    /**
     * Call when sale condition is match
     */
    fun onShow(event: SaleEvent, script: SaleScript)

    /**
     * Check if show sale condition is match
     */
    fun checkCondition(event: SaleEvent, script: SaleScript): Boolean = true
}

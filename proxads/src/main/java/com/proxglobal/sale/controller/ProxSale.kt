package com.proxglobal.sale.controller

import com.proxglobal.sale.data.RemoteConfigSource
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.Action

class ProxSale(private val source: RemoteConfigSource) {
    private val listenerMap = mutableMapOf<Int, OnShowSaleCallback>()


    fun showSale(
        scriptId: Int,
        listener: OnShowSaleCallback,
        onCancel: Action? = null,
    ) {
        if (listener != listenerMap[scriptId]) listenerMap[scriptId] = listener
        val script = source.getScript(scriptId)
        if (listener.checkCondition()) {
            when (script!!.showType) {
                SaleScript.TYPE_SHOW_POP_UP -> {
                    listener.onShowPopup()
                }
                SaleScript.TYPE_SHOW_FULL_SCREEN -> {
                    listener.onShowFullscreen()
                }
                SaleScript.TYPE_SHOW_ICON -> {
                    listener.onShowIcon()
                }
                SaleScript.TYPE_SHOW_BANNER -> {
                    listener.onShowBanner()
                }
            }
        } else onCancel?.invoke()
    }

    fun showSale(
        scriptId: Int,
        condition: () -> Boolean,
        listener: OnShowSaleCallback = listenerMap[scriptId]!!,
        onCancel: Action? = null,
    ) {
        val script = source.getScript(scriptId)
        if (condition()) {
            when (script!!.showType) {
                SaleScript.TYPE_SHOW_POP_UP -> {
                    listener.onShowPopup()
                }
                SaleScript.TYPE_SHOW_FULL_SCREEN -> {
                    listener.onShowFullscreen()
                }
                SaleScript.TYPE_SHOW_ICON -> {
                    listener.onShowIcon()
                }
                SaleScript.TYPE_SHOW_BANNER -> {
                    listener.onShowBanner()
                }
            }
        } else onCancel?.invoke()
    }
}

interface OnShowSaleCallback {
    fun onShowPopup(event: SaleEvent, script: SaleScript) {}
    fun onShowFullscreen(event: SaleEvent, script: SaleScript) {}
    fun onShowIcon(event: SaleEvent, script: SaleScript) {}
    fun onShowBanner(event: SaleEvent, script: SaleScript) {}
    fun checkCondition(event: SaleEvent, script: SaleScript): Boolean = true
}

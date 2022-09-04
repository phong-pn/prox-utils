package com.proxglobal.sale.controller.behavior

import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript

/**
 * Define how to show a sale script
 */
interface ShowSaleBehavior {
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
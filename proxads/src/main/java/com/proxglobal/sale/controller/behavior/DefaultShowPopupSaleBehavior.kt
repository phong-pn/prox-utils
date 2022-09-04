package com.proxglobal.sale.controller.behavior

import androidx.fragment.app.FragmentManager
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.ui.BottomSheetSaleOffDialog
import com.proxglobal.sale.ui.SaleOffDialog

open class DefaultShowPopupSaleBehavior(private val fragmentManager: FragmentManager) :
    ConditionOnShowSaleBehavior() {
    override fun onShow(event: SaleEvent, script: SaleScript) {
        super.onShow(event, script)
        if (event.isSaleOff) showSaleOffPopup(fragmentManager, event, script)
        else showDefaultSalePopup(event, script)
    }

    protected open fun showSaleOffPopup(
        fragmentManager: FragmentManager,
        event: SaleEvent,
        script: SaleScript
    ) {
        BottomSheetSaleOffDialog(script).show(fragmentManager, null)
    }

    protected open fun showDefaultSalePopup(event: SaleEvent, script: SaleScript) {

    }
}
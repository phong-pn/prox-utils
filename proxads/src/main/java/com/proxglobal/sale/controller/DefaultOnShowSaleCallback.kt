package com.proxglobal.sale.controller

import com.proxglobal.sale.data.sharepreference.ProxPreferences
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.model.sale.SaleScript.Companion.TYPE_CONDITION_COUNT_NUMBER
import java.util.*

class DefaultOnShowSaleCallback(
    private val script: SaleScript
) : OnShowSaleCallback {
    private fun increaseCount() {
        if (script.showConditionType == TYPE_CONDITION_COUNT_NUMBER) {
            val key = "sale_script_id_${script.scriptId}"
            var countOfScript = ProxPreferences.valueOf(key, 0)
            countOfScript++
            if (countOfScript >= script.showConditionValue!!) {
                ProxPreferences.setValue(key, 0)
            }
        }
    }

    override fun checkCondition(event: SaleEvent, script: SaleScript): Boolean {
        return when (script.showConditionType) {
            SaleScript.TYPE_CONDITION_COUNT_DOWN -> {
                if (event.endTime == null) true
                else {
                    if (script.showConditionValue == null) throw NullPointerException("show_condition_value is missing. Please sure that property is existed in Remote Config")
                    else (event.endTime!! - Date().time) / (1000 * 60) <= script.showConditionValue!!
                }
            }
            TYPE_CONDITION_COUNT_NUMBER -> {
                if (script.showConditionValue == null) throw NullPointerException("show_condition_value is missing. Please sure that property is existed in Remote Config")
                val key = "sale_script_id_${script.scriptId}"
                ProxPreferences.valueOf(key, 0) == script.showConditionValue
            }
            else -> true
        }
    }

    override fun onShowBanner(event: SaleEvent, script: SaleScript) {
        increaseCount()
    }

    override fun onShowFullscreen(event: SaleEvent, script: SaleScript) {
        increaseCount()
    }

    override fun onShowIcon(event: SaleEvent, script: SaleScript) {
        increaseCount()
    }

    override fun onShowPopup(event: SaleEvent, script: SaleScript) {
        increaseCount()
    }
}
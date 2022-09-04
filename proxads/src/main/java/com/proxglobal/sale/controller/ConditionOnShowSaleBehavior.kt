package com.proxglobal.sale.controller

import com.proxglobal.sale.controller.behavior.ShowSaleBehavior
import com.proxglobal.sale.data.sharepreference.ProxPreferences
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.model.sale.SaleScript.Companion.TYPE_CONDITION_COUNT_DOWN
import com.proxglobal.sale.model.sale.SaleScript.Companion.TYPE_CONDITION_COUNT_NUMBER
import java.util.*

/**
 * A class that implement a default checkCondition behavior of [ShowSaleBehavior]. It can check show sale condition for
 * script that has [SaleScript.showConditionType] is [TYPE_CONDITION_COUNT_NUMBER] or [TYPE_CONDITION_COUNT_DOWN]
 */
abstract class ConditionOnShowSaleBehavior : ShowSaleBehavior {
    private fun increaseCount(script: SaleScript) {
        if (script.showConditionType == TYPE_CONDITION_COUNT_NUMBER) {
            val key = "sale_script_id_${script.scriptId}"
            var countOfScript = ProxPreferences.valueOf(key, 0)
            countOfScript++
            ProxPreferences.setValue(
                key,
                if (countOfScript > script.showConditionValue!!) 0 else countOfScript
            )
        }
    }

    override fun checkCondition(event: SaleEvent, script: SaleScript): Boolean {
        return when (script.showConditionType) {
            TYPE_CONDITION_COUNT_DOWN -> {
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

    override fun onCancel(event: SaleEvent, script: SaleScript) {
        increaseCount(script)
    }

    override fun onShow(event: SaleEvent, script: SaleScript) {
        increaseCount(script)
    }
}
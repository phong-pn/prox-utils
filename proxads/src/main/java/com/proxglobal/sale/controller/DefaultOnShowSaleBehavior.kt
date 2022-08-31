package com.proxglobal.sale.controller

import android.content.Context
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.proxglobal.sale.data.sharepreference.ProxPreferences
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.model.sale.SaleScript.Companion.TYPE_CONDITION_COUNT_NUMBER
import com.proxglobal.sale.ui.BottomSheetSaleOffDialog
import com.proxglobal.sale.ui.SaleOffDialog
import com.proxglobal.sale.utils.logd
import java.util.*

open class DefaultOnShowSaleBehavior(
    private val fragmentManager: FragmentManager,
) : ShowSaleCallback {
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

    override fun onCancel(event: SaleEvent, script: SaleScript) {
        increaseCount(script)
    }

    override fun onShow(event: SaleEvent, script: SaleScript) {
        increaseCount(script)
        when (script.showType) {
            SaleScript.TYPE_SHOW_POP_UP -> {
                showPopup(event, script)
            }
            SaleScript.TYPE_SHOW_FULL_SCREEN -> {
                showFullscreen(event, script)
            }
            SaleScript.TYPE_SHOW_ICON -> {
                showIcon(event, script)
            }
            SaleScript.TYPE_SHOW_BANNER -> {
                showBanner(event, script)
            }
        }
    }

    open fun showBanner(container: FrameLayout, event: SaleEvent, script: SaleScript) {

        event.logd()
    }

    open fun showFullscreen(event: SaleEvent, script: SaleScript) {
        event.logd()
        if (event.isSaleOff) {
            showDefaultSaleFullscreen(event, script)
        } else {
            showSaleOffFullscreen(event, script)
        }
    }

    open fun showIcon(event: SaleEvent, script: SaleScript) {
        event.logd()
        if (event.isSaleOff) {
            showDefaultSalePopup(event, script)
        } else {
            showSaleOffPopup(event, script)
        }
    }

    private fun showPopup(event: SaleEvent, script: SaleScript) {
        if (event.isSaleOff) showSaleOffPopup(event, script) else showDefaultSalePopup(event, script)
        event.logd()
    }

    open fun showDefaultSaleFullscreen(event: SaleEvent, script: SaleScript) {

    }

    open fun showSaleOffFullscreen(event: SaleEvent, script: SaleScript) {

    }

    open fun showDefaultSalePopup(event: SaleEvent, script: SaleScript) {

    }

    open fun showSaleOffPopup(event: SaleEvent, script: SaleScript) {
        SaleOffDialog().show(fragmentManager, null)
    }
}
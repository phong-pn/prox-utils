package com.proxglobal.sale.controller.behavior

import com.google.firebase.inappmessaging.ktx.inAppMessaging
import com.google.firebase.ktx.Firebase
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript

class DefaultShowInAppMessingBehavior(private val triggerEventId: String = "on_foreground"): ConditionOnShowSaleBehavior() {
    override fun onShow(event: SaleEvent, script: SaleScript) {
        // temporarily disable showing message. Message is shown whenever calling setMessagesSuppressed(false), or app start
        Firebase.inAppMessaging.setMessagesSuppressed(false)
        Firebase.inAppMessaging.triggerEvent(triggerEventId)
    }

    override fun onCancel(event: SaleEvent, script: SaleScript) {
        // enable showing message
        Firebase.inAppMessaging.setMessagesSuppressed(true)
    }
}
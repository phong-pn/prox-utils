package com.proxglobal.sale.data

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.proxglobal.purchase.ProxPurchase
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.logd

class RemoteConfigSource {
    private var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        })
    }

    private val scripts = mutableListOf<SaleScript>()

    internal fun getScript(id: Int) = scripts.find { it.scriptId == id }

    internal var event: SaleEvent = SaleEvent()

    internal fun fetch(application: Application) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            val saleKeyConfigs = remoteConfig.getKeysByPrefix("sale")
            saleKeyConfigs.logd()
            if (saleKeyConfigs.size == 1) { // sale default
                event = Gson().fromJson(
                    remoteConfig.getString(saleKeyConfigs.first()),
                    SaleEvent::class.java
                )
            } else {
                // Have some sale-off event, so we need choose the best sale event match some condition. The sale event must
                // First, be started and not ended
                // Second, be enable
                // Third, if 2 or more sale-off event match conditions, choose the first. If have no sale-off event is chosen, choose the default sale-event
                val saleConfigs = saleKeyConfigs.map { remoteConfig.getString(it) }
                val saleEvents =
                    saleConfigs.map { Gson().fromJson(it, SaleEvent::class.java) }.toMutableList()
                for (i in saleEvents.indices) {
                    if (i < saleEvents.size && saleEvents[i].enable == false) {
                        saleEvents.remove(saleEvents[i])
                    }
                }
                if (saleEvents.size > 2) {
                    // Remove default sale-event
                    saleEvents.remove(saleEvents.find { !it.isSaleOff })
                    event = saleEvents.first()
                } else {
                    event =
                        saleEvents.find { it.isSaleOff } // find the sale-off event. If null, find the default sale-event
                            ?: saleEvents.find { !it.isSaleOff }!!
                }
            }
            event.isSaleOff.logd()
            scripts.addAll(event.saleScripts!!)
            ProxPurchase.getInstance().initBilling(application, null, event.subItems.let {
                listOf(it?.foreverSale, it?.yearSale, it?.monthSale).mapNotNull { it }
            })
        }
    }
}
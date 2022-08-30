package com.proxglobal.sale.data

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.SALE_DEFAULT
import java.util.*

class RemoteConfigSource {
    private var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
    }

    private val scripts = mutableListOf<SaleScript>()

    fun getScript(id: Int) = scripts.find { it.scriptId == id }

    lateinit var event: SaleEvent

    fun fetch(application: Application) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            val saleConfigs = remoteConfig.getKeysByPrefix("sale")
            if (saleConfigs.size == 1) { // sale default
                event = Gson().fromJson(
                    remoteConfig.getString(saleConfigs.first()),
                    SaleEvent::class.java
                )
            } else {
                // Have some sale-off event, so we need choose the best sale event match some condition. The sale event must
                // First, be not the sale default
                // Second, if event is started and not ended
                saleConfigs.remove(SALE_DEFAULT)
                val saleEvents = saleConfigs.map { Gson().fromJson(it, SaleEvent::class.java) }.toMutableList()
                saleEvents.forEach {
                    // Remove event in the future
                    if (it.startTime!! > System.currentTimeMillis()) saleEvents.remove(it)
                    //Remove event in the past
                    if (it.endTime!! < System.currentTimeMillis()) saleEvents.remove(it)
                }
            }
        }
    }
}
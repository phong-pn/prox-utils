package com.proxglobal.lib.testsale

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.proxglobal.lib.MainActivity3
import com.proxglobal.lib.R
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.controller.ProxSale
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript


class TestSaleOffActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_sale_off)
        findViewById<View>(R.id.bt_download).setOnClickListener {
            ProxSale.modifyShowingInAppMessaging(true)

            ProxSale.showPopup(supportFragmentManager, 1)
        }
        findViewById<View>(R.id.bt_play).setOnClickListener {
            ProxSale.showFullscreen(2, object : ConditionOnShowSaleBehavior() {

                override fun onShow(event: SaleEvent, script: SaleScript) {
                    super.onShow(event, script)


                }
            })
        }
        findViewById<View>(R.id.bt_pause).setOnClickListener {
            ProxSale.showBanner(findViewById(R.id.banner_container), 3)
        }
        findViewById<View>(R.id.bt_next).setOnClickListener {
            ProxSale.showFullscreen(4, object : ConditionOnShowSaleBehavior() {
                override fun onShow(event: SaleEvent, script: SaleScript) {
                    super.onShow(event, script)

                }
            })
        }
        ProxSale.registerInAppMessagingListener(
            onClick = { message, action ->
                if (action.button?.text?.text == "Get go")
                    startActivity(Intent(this, MainActivity3::class.java))
            }
        )
    }
}
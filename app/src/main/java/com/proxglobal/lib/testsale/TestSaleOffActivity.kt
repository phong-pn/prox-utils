package com.proxglobal.lib.testsale

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.phongpn.countdown.countdown.CountDownView
import com.phongpn.countdown.util.dp
import com.proxglobal.lib.MainActivity3
import com.proxglobal.lib.R
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.controller.ProxSale
import com.proxglobal.sale.controller.behavior.DefaultShowBannerSaleBehavior
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript


class TestSaleOffActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_sale_off)
        ProxSale.showInAppMessaging()
        findViewById<View>(R.id.bt_download).setOnClickListener {
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
            ProxSale.showBanner(
                findViewById(R.id.banner_container),
                3,
                object : DefaultShowBannerSaleBehavior(findViewById(R.id.banner_container)) {
                    override fun onShow(event: SaleEvent, script: SaleScript) {
                        findViewById<CountDownView>(R.id.count_down)
                            .modifier {
                                timePaddingLeft = 2.dp
                                timePaddingRight = 2.dp
                            }
                        findViewById<View>(R.id.banner).isVisible = true
                        super.onShow(event, script)
                    }

                    override fun onCancel(event: SaleEvent, script: SaleScript) {
                        super.onCancel(event, script)
                        findViewById<View>(R.id.banner).isVisible = false
                    }
                })

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
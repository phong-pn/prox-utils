package com.proxglobal.lib.testsale

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.ktx.Firebase
import com.phongpn.countdown.countdownview.CountDownConfig
import com.proxglobal.countdownview.countdownview.CountdownView
import com.proxglobal.lib.MainActivity3
import com.proxglobal.lib.R
import com.proxglobal.sale.controller.ConditionOnShowSaleBehavior
import com.proxglobal.sale.controller.ProxSale
import com.proxglobal.sale.controller.behavior.DefaultShowBannerSaleBehavior
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.dp


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
            ProxSale.showBanner(findViewById(R.id.banner_container), 3, object: DefaultShowBannerSaleBehavior(findViewById(R.id.banner_container)) {
                override fun onShow(event: SaleEvent, script: SaleScript) {
                    findViewById<CountdownView>(R.id.count_down).apply {
                        dynamicShow(CountDownConfig(
                            suffixTextSize = 16f,
                        ))
                    }.start(30000)
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
//        findViewById<CountdownView>(R.id.count_down).apply {
//            dynamicShow(DynamicConfig.Builder()
//                .setBackgroundInfo(
//                    DynamicConfig.BackgroundInfo()
//                        .setColor(Color.parseColor("#FFCC0000"))
//                        .setSize(60.dp)
//                        .setRadius(2.dp)
//                        .setShowTimeBgDivisionLine(false)
//                )
//                .build())
//            start(3600000)
//        }
    }
}
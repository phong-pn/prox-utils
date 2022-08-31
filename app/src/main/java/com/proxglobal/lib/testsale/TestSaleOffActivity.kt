package com.proxglobal.lib.testsale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.proxglobal.lib.R
import com.proxglobal.sale.controller.DefaultOnShowSaleBehavior
import com.proxglobal.sale.controller.ProxSale
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript

class TestSaleOffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_sale_off)
        findViewById<View>(R.id.bt_download).setOnClickListener {
            ProxSale.showSale(1, DefaultOnShowSaleBehavior(supportFragmentManager))
        }
        findViewById<View>(R.id.bt_play).setOnClickListener {
            ProxSale.showSale(2, DefaultOnShowSaleBehavior(supportFragmentManager))
        }
        findViewById<View>(R.id.bt_pause).setOnClickListener {
            ProxSale.showSale(3, DefaultOnShowSaleBehavior(supportFragmentManager))
        }
        findViewById<View>(R.id.bt_next).setOnClickListener {
            ProxSale.showSale(4, object : DefaultOnShowSaleBehavior(supportFragmentManager){
                override fun onCancel(event: SaleEvent, script: SaleScript) {
                    super.onCancel(event, script)
                }

                override fun checkCondition(event: SaleEvent, script: SaleScript): Boolean {
                    return super.checkCondition(event, script)
                }

                override fun showSaleOffPopup(event: SaleEvent, script: SaleScript) {
                    super.showSaleOffPopup(event, script)
                }
            })
        }

    }
}
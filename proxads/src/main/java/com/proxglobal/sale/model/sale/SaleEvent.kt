package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaleEvent (
    @SerializedName("start_time")
    @Expose
    var startTime: Long? = null,

    @SerializedName("end_time")
    @Expose
    var endTime: Long? = null,

    @SerializedName("content")
    @Expose
    var saleEventContent: SaleEventContent? = null,

    @SerializedName("saleoff_percent")
    @Expose
    var saleoffPercent: Int? = null,

    @SerializedName("id_price")
    @Expose
    var subItems: SubItemsPurchase? = null,

    @SerializedName("script")
    @Expose
    var saleScripts: List<SaleScript>? = null
)
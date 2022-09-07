package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubItemsPurchase (
    @SerializedName("month")
    @Expose
    var monthlyId: String? = null,

    @SerializedName("year")
    @Expose
    var yearlyId: String? = null,

    @SerializedName("forever")
    @Expose
    var foreverId: String? = null
)
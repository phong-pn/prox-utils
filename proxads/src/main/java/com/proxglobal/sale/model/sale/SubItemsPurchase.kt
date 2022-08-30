package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubItemsPurchase (
    @SerializedName("month_sale")
    @Expose
    var monthSale: String? = null,

    @SerializedName("year_sale")
    @Expose
    var yearSale: String? = null,

    @SerializedName("forever_sale")
    @Expose
    var foreverSale: String? = null
)
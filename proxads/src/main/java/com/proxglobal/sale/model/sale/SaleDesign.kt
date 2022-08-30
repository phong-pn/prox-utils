package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SaleDesign (
    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("background")
    @Expose
    var background: String? = null
)
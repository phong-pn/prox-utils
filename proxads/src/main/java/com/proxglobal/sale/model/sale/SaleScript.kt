package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaleScript {
    @SerializedName("script_id")
    @Expose
    var scriptId: Int = 0

    @SerializedName("show_type")
    @Expose
    var showType: String = TYPE_SHOW_POP_UP

    @SerializedName("show_condition_value")
    @Expose
    var showConditionValue: Int? = null

    @SerializedName("show_condition_type")
    @Expose
    var showConditionType: String? = null

    @SerializedName("design")
    @Expose
    var saleDesign: SaleDesign? = null

    companion object {
        const val TYPE_SHOW_POP_UP = "pop_up"
        const val TYPE_SHOW_BANNER = "banner"
        const val TYPE_SHOW_ICON = "icon"
        const val TYPE_SHOW_FULL_SCREEN = "full_screen"

        const val TYPE_CONDITION_COUNT_DOWN = "count_down"
        const val TYPE_CONDITION_COUNT_NUMBER = "count_number"
    }
}
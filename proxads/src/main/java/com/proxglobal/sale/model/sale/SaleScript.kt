package com.proxglobal.sale.model.sale

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SaleScript {
    @SerializedName("script_name")
    @Expose
    var scriptName: String? = null

    @SerializedName("action_id")
    @Expose
    var actionId: Int = 0

    @SerializedName("show_type")
    @Expose
    var showType: String = TYPE_SHOW_POP_UP

    @SerializedName("show_condition_value")
    @Expose
    val showConditionValue: Any? = null


    @SerializedName("show_condition_type")
    @Expose
    var showConditionType: String? = null

    @SerializedName("design")
    @Expose
    var saleDesign: SaleDesign? = null

    @SerializedName("content")
    @Expose
    var scriptContent: SaleContent? = null

    companion object {
        const val TYPE_SHOW_POP_UP = "pop_up"
        const val TYPE_SHOW_BANNER = "banner"
        const val TYPE_SHOW_ICON = "icon"
        const val TYPE_SHOW_FULL_SCREEN = "full_screen"
        const val TYPE_SHOW_IN_APP_MESSAGE = "in_app_message"

        const val TYPE_CONDITION_COUNT_DOWN = "count_down"
        const val TYPE_CONDITION_COUNT_NUMBER = "count_number"
        const val TYPE_CONDITION_BOOLEAN = "true_false"
    }
}
package com.proxglobal.sale.controller

import android.app.Activity
import android.app.Application
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.display.FiamListener
import com.google.firebase.inappmessaging.display.ktx.inAppMessagingDisplay
import com.google.firebase.inappmessaging.ktx.inAppMessaging
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.proxglobal.sale.controller.behavior.DefaultShowBannerSaleBehavior
import com.proxglobal.sale.controller.behavior.DefaultShowInAppMessingBehavior
import com.proxglobal.sale.controller.behavior.DefaultShowPopupSaleBehavior
import com.proxglobal.sale.controller.behavior.ShowSaleBehavior
import com.proxglobal.sale.data.RemoteConfigSource
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.logFirebaseEvent
import com.proxglobal.sale.utils.logd

/**
 * Controller for show sale script
 */
object ProxSale {
    private val source = RemoteConfigSource()

    fun fetch(application: Application) {
        source.fetch(application)
        setShowInAppMessagingBehavior(DefaultShowInAppMessingBehavior())
    }

    init {
        Firebase.messaging.token.addOnSuccessListener {
            it.logd()
        }
    }

    val currentSaleEvent: SaleEvent
        get() = source.event

    /**
     * Show a script with with [behavior]
     * @see ShowSaleBehavior
     */
    fun showSale(
        scriptId: Int,
        behavior: ShowSaleBehavior,
    ) {
        val script = source.getScript(scriptId)!!
        if (behavior.checkCondition(currentSaleEvent, script)) {
            behavior.onShow(currentSaleEvent, script)
        } else behavior.onCancel(currentSaleEvent, script)
    }

    /**
     * Show a banner script. By default, only banner image will be hold inside [container].
     * If you want custom your showing behavior, like adding a count down, you can custom [ShowSaleBehavior]
     * and after that, set [behavior] with your custom behavior
     * @param container: a [FrameLayout] hold the banner view
     * @param scriptId: id of script
     * @param behavior: showing banner behavior. By default, this is a [DefaultShowBannerSaleBehavior]
     */
    fun showBanner(
        container: FrameLayout,
        scriptId: Int,
        behavior: ShowSaleBehavior = DefaultShowBannerSaleBehavior(container)
    ) {
        showSale(scriptId, behavior)
    }

    fun showPopup(
        fragmentManager: FragmentManager,
        scriptId: Int,
        behavior: ShowSaleBehavior = DefaultShowPopupSaleBehavior(fragmentManager)
    ) = showSale(scriptId, behavior)

    fun showFullscreen(
        scriptId: Int,
        behavior: ShowSaleBehavior
    ) = showSale(scriptId, behavior)


    fun setShowInAppMessagingBehavior(behavior: ShowSaleBehavior) {
        if (source.inAppMessageScript != null) showSale(source.inAppMessageScript!!.scriptId, behavior)
    }

    /**
     * Enable or disable showing message. If [shouldShow] is false, the showing is disable, otherwise enable.
     * For example, disable showing a sale off message when user in a payment screen. Remember you may need enable the showing
     * after disable the showing
     */
    fun modifyShowingInAppMessaging(shouldShow: Boolean) {
        Firebase.inAppMessaging.setMessagesSuppressed(!shouldShow)
        if (shouldShow) {
            Firebase.inAppMessaging.triggerEvent("ready_display")
        }
    }

    /**
     * Register [Firebase.inAppMessaging] listener for showing message
     */
    fun registerInAppMessagingListener(
        onClick: (message: InAppMessage, action: Action) -> Unit = { _, _ -> },
        onDismiss: (message: InAppMessage) -> Unit = { _ -> },
        onDisplayError: (message: InAppMessage, reson: FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason) -> Unit = { _, reson -> reson.logd()},
        onDisplay: (message: InAppMessage) -> Unit = { _ -> "display".logd()},
    ) {
        Firebase.inAppMessaging.apply {
            addClickListener(onClick)
            addDismissListener(onDismiss)
            addDisplayErrorListener(onDisplayError)
            addImpressionListener(onDisplay)
        }
    }

    private val RemoteConfigSource.inAppMessageScript: SaleScript?
        get() {
            return scripts.find { it.showConditionType == SaleScript.TYPE_SHOW_IN_APP_MESSAGE }
        }
}


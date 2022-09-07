package com.proxglobal.sale.controller

import android.app.Application
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.ktx.inAppMessaging
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.proxglobal.purchase.ProxPurchase
import com.proxglobal.sale.controller.behavior.DefaultShowBannerSaleBehavior
import com.proxglobal.sale.controller.behavior.DefaultShowInAppMessingBehavior
import com.proxglobal.sale.controller.behavior.DefaultShowPopupSaleBehavior
import com.proxglobal.sale.controller.behavior.ShowSaleBehavior
import com.proxglobal.sale.data.RemoteConfigSource
import com.proxglobal.sale.model.sale.SaleEvent
import com.proxglobal.sale.model.sale.SaleScript
import com.proxglobal.sale.utils.logd

/**
 * Controller for show sale script
 */
object ProxSale {
    private val source = RemoteConfigSource()

    fun fetch(application: Application) {
        source.fetch(application)
    }

    init {
        Firebase.messaging.token.addOnSuccessListener {
            it.logd()
        }
        // temporarily disable show in-app-messge
        Firebase.inAppMessaging.setMessagesSuppressed(true)
    }

    val currentSaleEvent: SaleEvent
        get() = source.event

    /**
     * Show a script with with [behavior]
     * @see ShowSaleBehavior
     */
    fun showSale(
        actionId: Int,
        behavior: ShowSaleBehavior,
    ) {
        if (!ProxPurchase.getInstance().checkPurchased()) {
            val script = source.getScript(actionId)!!
            if (behavior.checkCondition(currentSaleEvent, script)) {
                behavior.onShow(currentSaleEvent, script)
            } else behavior.onCancel(currentSaleEvent, script)
        }
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
        actionId: Int,
        behavior: ShowSaleBehavior = DefaultShowBannerSaleBehavior(container)
    ) {
        showSale(actionId, behavior)
    }

    fun showPopup(
        fragmentManager: FragmentManager,
        actionId: Int,
        behavior: ShowSaleBehavior = DefaultShowPopupSaleBehavior(fragmentManager)
    ) = showSale(actionId, behavior)

    fun showFullscreen(
        actionId: Int,
        behavior: ShowSaleBehavior
    ) = showSale(actionId, behavior)

    /**
     * Programmatically show in-app-message. Note that [triggerEvent] must be match with trigger eventID
     * of message.
     *@see <a href = "https://firebase.google.com/docs/in-app-messaging/modify-message-behavior?hl=en&authuser=0&platform=android#trigger_in-app_messages_programmatically_2">See more about trigger event</a>
     */
    fun showInAppMessaging(triggerEvent: String = "on_foreground", behavior: ShowSaleBehavior = DefaultShowInAppMessingBehavior()) {
        if (source.inAppMessageScript != null) showSale(source.inAppMessageScript!!.actionId, behavior)
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
            return event.saleScripts?.find { it.showConditionType == SaleScript.TYPE_SHOW_IN_APP_MESSAGE }
        }
}


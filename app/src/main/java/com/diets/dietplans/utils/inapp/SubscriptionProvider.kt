package com.diets.dietplans.utils.inapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.billingclient.api.*
import com.diets.dietplans.premium.IDS
import com.diets.dietplans.utils.PreferenceProvider
import com.diets.dietplans.utils.analytics.Ampl
import java.util.*
import kotlin.collections.HashMap

object SubscriptionProvider : PurchasesUpdatedListener, BillingClientStateListener {

    lateinit private var playStoreBillingClient: BillingClient
    private lateinit var preferences: SharedPreferences
    private val skuDetails: MutableMap<String, SkuDetails?> =
            HashMap()

    private const val TRACKER_TAG = "TRACKER_TAG"

    private var inAppCallback: InAppCallback? = null

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        if (billingResult!!.responseCode == BillingClient.BillingResponseCode.OK) {
            if (purchases != null && purchases.size > 0 && purchases[0].purchaseState == Purchase.PurchaseState.PURCHASED) {
                inAppCallback?.trialSucces()
                if (!purchases[0].isAcknowledged) {
                    val params = AcknowledgePurchaseParams
                            .newBuilder()
                            .setPurchaseToken(purchases[0].purchaseToken)
                            .build()
                    var listener = AcknowledgePurchaseResponseListener {
                        Log.e("LOL", "confirmed")
                    }
                    playStoreBillingClient.acknowledgePurchase(params, listener)
                }
            }
        }
    }


    fun init(context: Context) {
        preferences = context.getSharedPreferences("subscription", Context.MODE_PRIVATE)
        playStoreBillingClient = BillingClient.newBuilder(context.applicationContext)
                .enablePendingPurchases() // required or app will crash
                .setListener(this).build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        if (!playStoreBillingClient.isReady) {
            playStoreBillingClient.startConnection(this)
            return true
        }
        return false
    }


    override fun onBillingServiceDisconnected() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            var hasSubscription = false
            val result = playStoreBillingClient.queryPurchases(BillingClient.SkuType.SUBS)
            if (result != null && result.purchasesList != null && result.purchasesList!!.size > 0) {
                hasSubscription = true
            }
            PreferenceProvider.isHasPremium = hasSubscription
        }
    }


    fun choiceSubNew(activity: Activity, subId: String, callback: InAppCallback) {
        Ampl.attemptBilling()
        inAppCallback = callback
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(subId))
                .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    Ampl.succeesBilling()
                    if (skuDetailsList.orEmpty().isNotEmpty()) {
                        Ampl.succeesBillingAndNotEmpty()
                        skuDetailsList!!.forEach {
                            skuDetails[TRACKER_TAG] = it
                            val perchaseParams = BillingFlowParams.newBuilder().setSkuDetails(it)
                                    .build()
                            playStoreBillingClient.launchBillingFlow(activity, perchaseParams)
                        }
                    }
                }
                else -> {
                    Ampl.errorBilling(billingResult.responseCode)
                }
            }
        }
    }

    fun startGettingPrice(): String {
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(IDS.WHITE_MONTH, IDS.WHITE_YEAR))
                .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            Log.e("LOL", billingResult.responseCode.toString())
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList!!.isNotEmpty()) {
                        try {
                            PreferenceProvider.premiumUnit = Currency.getInstance(skuDetailsList!![0].priceCurrencyCode).symbol
                            PreferenceProvider.monthPriceValue = skuDetailsList!![0].priceAmountMicros.toFloat() / 1_000_000

                            PreferenceProvider.yearPriceValue = skuDetailsList!![1].priceAmountMicros.toFloat() / 1_000_000
                            Log.e("LOL", "1 ---- ${skuDetailsList!![0].priceAmountMicros.toFloat() / 1_000_000}, 2--- ${skuDetailsList!![1].priceAmountMicros.toFloat() / 1_000_000}")
                        } catch (ex: Exception) {
                            Log.e("LOL", "catch")
                        }
                    }
                }
            }
        }
        return ""
    }


}
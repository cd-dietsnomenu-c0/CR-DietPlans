package com.diets.dietplans.utils.ad

import android.content.Context
import com.diets.dietplans.R
import com.diets.dietplans.utils.PreferenceProvider
import com.diets.dietplans.utils.analytics.Ampl
import com.diets.dietplans.utils.analytics.FBAnalytic
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import java.util.*

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var inter: InterstitialAd? = null
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var isFailedLoad = false

    //var adsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    //var bufferAdsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    //var adLoader: AdLoader? = null
    var nativeSpeaker: NativeSpeaker? = null
    var isNeedShowNow = false

    init {
        FrequencyManager.runSetup()
    }

    fun init(context: Context) {
        inter = InterstitialAd(context)
        inter?.setAdUnitId(context.getString(R.string.interstitial_id))
        inter?.loadAd(AdRequest.Builder().build())
        //loadNative(context)
        inter?.setInterstitialAdEventListener(object : InterstitialAdEventListener {

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Ampl.failedOneLoads()
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    Ampl.failedAllLoads()
                    isFailedLoad = true
                }
            }

            override fun onAdShown() {
            }

            override fun onAdDismissed() {
                inter?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdClicked() {
            }

            override fun onLeftApplication() {
            }

            override fun onReturnedToApplication() {
            }

            override fun onImpression(p0: ImpressionData?) {
            }


            override fun onAdLoaded() {
                if (isNeedShowNow && needShow()) {
                    isNeedShowNow = false
                    inter?.show()
                    Ampl.showAd()
                }
            }
        })
    }

    /*private fun loadNative(context: Context) {
        if (!PreferenceProvider.isHasPremium) {
            adLoader = AdLoader
                    .Builder(context, context.getString(R.string.native_ad))
                    .forUnifiedNativeAd { nativeAD ->
                        bufferAdsList.add(nativeAD)
                        if (!adLoader!!.isLoading) {
                            endLoading()
                        }
                    }.withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(p0: Int) {
                            if (!adLoader!!.isLoading) {
                                endLoading()
                            }
                        }
                    }).build()
            adLoader?.loadAds(AdRequest.Builder().build(), Config.NATIVE_ITEMS_MAX)
        }
    }*/

    private fun endLoading() {
        /*if (bufferAdsList.size > 0) {
            adsList = bufferAdsList
            bufferAdsList = arrayListOf()
            nativeSpeaker?.loadFin(adsList)
        }*/
    }

    fun observeOnNativeList(nativeSpeaker: NativeSpeaker) {
        /*if (!PreferenceProvider.isHasPremium) {
            if (adsList.size > 0) {
                nativeSpeaker.loadFin(adsList)
            } else {
                this.nativeSpeaker = nativeSpeaker
            }
        }*/
    }

    fun refreshNativeAd(context: Context) {
        /*nativeSpeaker = null
        loadNative(context)*/
    }

    private fun reload() {
        inter?.loadAd(AdRequest.Builder().build())
    }

    fun checkLoad() {
        if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInter() {
        if (needShow() && !PreferenceProvider.isHasPremium) {
            if (Counter.getInstance().getCounter() % MAX_REQUEST_AD == 0) {
                if (inter?.isLoaded == true) {
                    inter?.show()
                    Ampl.showAd()
                    Counter.getInstance().adToCounter()
                } else if (isFailedLoad) {
                    counterFailed = 0
                    isFailedLoad = false
                    reload()
                }
            } else {
                Counter.getInstance().adToCounter()
            }
        }
    }

    fun getShow() {
        if (!PreferenceProvider.isHasPremium) {
            if (inter?.isLoaded == true && needShow()) {
                inter?.show()
            } else {
                isNeedShowNow = true
            }
        }
    }

    private fun needShow(): Boolean {
        return Random().nextInt() <= PreferenceProvider.frequencyPercent
    }

    fun showInterWithoutCounter() {
        if (!PreferenceProvider.isHasPremium && needShow()) {
            if (inter?.isLoaded == true) {
                FBAnalytic.adShow()
                inter?.show()
                Ampl.showAd()
            } else if (isFailedLoad) {
                counterFailed = 0
                isFailedLoad = false
                reload()
            }
        }
    }
}

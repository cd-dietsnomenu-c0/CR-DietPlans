package com.diets.dietplans.utils.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import com.diets.dietplans.App
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.diets.dietplans.Config
import com.diets.dietplans.R
import com.diets.dietplans.utils.PreferenceProvider
import com.diets.dietplans.utils.analytics.Ampl
import com.diets.dietplans.utils.analytics.FBAnalytic
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlin.random.Random

object AdWorker {
    private const val MAX_REQUEST_AD = 3
    private var inter: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var isFailedLoad = false
    var adsList: ArrayList<NativeAd> = arrayListOf()
    var bufferAdsList: ArrayList<NativeAd> = arrayListOf()
    var adLoader: AdLoader? = null
    var nativeSpeaker: NativeSpeaker? = null
    var isNeedShowNow = false
    private var counterRewardFailed = 0
    private const val MAX_QUERY_REWARD_VIDEO = 3

    init {
        FrequencyManager.runSetup()
    }

    var fullScreenContentCallback: FullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            inter = null
            loadInter()
        }
    }


    fun init(context: Context) {
        loadNative(context)
        loadInter()
    }

    private fun loadInter() {
        InterstitialAd.load(App.getInstance(), App.getInstance().getString(R.string.interstitial_id), AdRequest.Builder().build(), object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.e("LOL", "onAdFailedToLoad")
                Ampl.failedOneLoads(p0.message)
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    Ampl.failedAllLoads(p0.message)
                    isFailedLoad = true
                }
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                Log.e("LOL", "onAdLoaded")
                if (needShow()) {
                    isNeedShowNow = false
                    inter = p0
                    inter!!.fullScreenContentCallback = fullScreenContentCallback
                    Ampl.showAd()
                }
            }
        })


    }

    private fun loadNative(context: Context) {
        if (!Config.FOR_TEST) {
            if (!PreferenceProvider.isHasPremium) {
                adLoader = AdLoader
                        .Builder(context, context.getString(R.string.native_ad))
                        .forNativeAd { nativeAD ->
                            bufferAdsList.add(nativeAD)
                            if (!adLoader!!.isLoading) {
                                endLoading()
                            }
                        }.withAdListener(object : AdListener() {
                            override fun onAdFailedToLoad(p0: LoadAdError) {
                                if (!adLoader!!.isLoading) {
                                    endLoading()
                                }
                            }
                        }).build()
                adLoader?.loadAds(AdRequest.Builder().build(), Config.NATIVE_ITEMS_MAX)
            }
        }
    }



    fun getRewardAd() : RewardedAd?{
        return rewardedAd
    }

    private fun endLoading() {
        if (bufferAdsList.size > 0) {
            adsList = bufferAdsList
            bufferAdsList = arrayListOf()
            nativeSpeaker?.loadFin(adsList)
        }
    }

    fun observeOnNativeList(nativeSpeaker: NativeSpeaker) {
        if (!PreferenceProvider.isHasPremium) {
            if (adsList.size > 0) {
                nativeSpeaker.loadFin(adsList)
            } else {
                this.nativeSpeaker = nativeSpeaker
            }
        }
    }

    fun refreshNativeAd(context: Context) {
        nativeSpeaker = null
        loadNative(context)
    }

    private fun reload() {
        loadInter()
    }

    fun checkLoad() {
        if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInter(activity: Activity) {
        if (!PreferenceProvider.isHasPremium && needShow() && !Config.FOR_TEST) {
            if (Counter.getInstance().getCounter() % MAX_REQUEST_AD == 0) {
                if (inter != null) {
                    FBAnalytic.adShow()
                    inter?.show(activity)
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


    fun showInterWithoutCounter(activity: Activity) {
        if (!PreferenceProvider.isHasPremium && needShow()) {
            if (inter != null) {
                FBAnalytic.adShow()
                inter?.show(activity)
                Ampl.showAd()
            } else if (isFailedLoad) {
                counterFailed = 0
                isFailedLoad = false
                reload()
            }
        }
    }

    fun getShow(activity: Activity) {
        if (!PreferenceProvider.isHasPremium) {
            if (inter != null && needShow()) {
                FBAnalytic.adShow()
                inter?.show(activity)
            } else {
                isNeedShowNow = true
            }
        }
    }

    private fun needShow(): Boolean {
        if (Config.FOR_TEST) {
            return false
        } else {
            return Random.nextInt(100) <= PreferenceProvider.frequencyPercent
        }
    }
}

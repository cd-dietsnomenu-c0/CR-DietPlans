package com.diets.dietplans.presentation.diets.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diets.dietplans.R
import com.diets.dietplans.utils.analytics.Ampl
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import kotlinx.android.synthetic.main.vh_ad_types.view.*

class ADVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.vh_ad_types, viewGroup, false)) {


    fun bind(unifiedNativeAd: NativeAd) {
        bindAdView(unifiedNativeAd)
    }

    private fun bindAdView(nativeAd: NativeAd){
        var nativeAdViewBinder = NativeAdViewBinder
                .Builder(itemView.ad_view)
                .setCallToActionView(itemView.ad_call_to_action)
                .setIconView(itemView.ad_icon)
                .setMediaView(itemView.ad_media)
                .setTitleView(itemView.ad_headline)
                .setSponsoredView(itemView.ad_body)
                .setWarningView(itemView.ad_warning)
                .build()

        try{
            nativeAd.bindNativeAd(nativeAdViewBinder)
            itemView.flAdContainer.visibility = View.VISIBLE
        }catch (ex : Exception){
            Ampl.errorNative(ex.toString())
        }
    }
}
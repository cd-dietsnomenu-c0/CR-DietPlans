package com.diets.dietplans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.dietplans.utils.ad.AdWorker
import com.diets.dietplans.utils.ad.NativeSpeaker
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import kotlinx.android.synthetic.main.ya_test.*
import java.util.*

class YaTest : AppCompatActivity(R.layout.ya_test) {

    var isFirst = true
    var nativeLists: ArrayList<NativeAd> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var nativeAdViewBinder1 = NativeAdViewBinder
                .Builder(ad_view1)
                .setCallToActionView(ad_call_to_action1)
                .setIconView(ad_icon1)
                .setMediaView(ad_media1)
                .setTitleView(ad_headline1)
                .setSponsoredView(ad_body1)
                .setWarningView(ad_warning1)
                .build()


        var nativeAdViewBinder = NativeAdViewBinder
                .Builder(ad_view)
                .setCallToActionView(ad_call_to_action)
                .setIconView(ad_icon)
                .setMediaView(ad_media)
                .setTitleView(ad_headline)
                .setSponsoredView(ad_body)
                .setWarningView(ad_warning)
                .build()

        AdWorker.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeList: ArrayList<NativeAd>) {
                nativeLists = nativeList
            }
        })

        btn.setOnClickListener {
            if (isFirst){
                nativeLists[0].bindNativeAd(nativeAdViewBinder1)
            }else{
                nativeLists[0].bindNativeAd(nativeAdViewBinder)
            }
            isFirst = !isFirst
        }


    }
}
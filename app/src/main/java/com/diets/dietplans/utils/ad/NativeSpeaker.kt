package com.diets.dietplans.utils.ad

import com.yandex.mobile.ads.nativeads.NativeAd
import java.util.*


interface NativeSpeaker {
    fun loadFin(nativeList : ArrayList<NativeAd>)
}
package com.diets.dietplans.utils.ad

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAd

interface NativeSpeaker {
    fun loadFin(nativeList : ArrayList<NativeAd>)
}
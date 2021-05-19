package com.diets.dietplans.utils.ad

import com.google.android.gms.ads.formats.UnifiedNativeAd

interface NativeSpeaker {
    fun loadFin(nativeList : ArrayList<UnifiedNativeAd>)
}
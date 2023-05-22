package com.diets.dietplans.utils.ad

import android.app.Activity
import com.diets.dietplans.utils.PreferenceProvider

object ActionAd {

    private const val ACTION_TRIGGER = 5

    fun action(activity: Activity) {
        var actionNumber = PreferenceProvider.actionNumber
        actionNumber++

        if (actionNumber >= ACTION_TRIGGER) {
            AdWorker.showInterWithoutCounter(activity)
            actionNumber = 0
        }

        PreferenceProvider.actionNumber = actionNumber
    }

}
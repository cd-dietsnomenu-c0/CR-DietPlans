package com.diets.dietplans.utils.ad

import com.diets.dietplans.utils.PreferenceProvider

object ActionAd {

    private const val ACTION_TRIGGER = 5

    fun action() {
        var actionNumber = PreferenceProvider.actionNumber
        actionNumber++

        if (actionNumber >= ACTION_TRIGGER) {
            AdWorker.showInterWithoutCounter()
            actionNumber = 0
        }

        PreferenceProvider.actionNumber = actionNumber
    }

}
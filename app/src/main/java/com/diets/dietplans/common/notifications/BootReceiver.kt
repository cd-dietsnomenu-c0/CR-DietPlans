package com.diets.dietplans.common.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
       BootNotificationService.enqueueWork(context!!, Intent())
    }
}
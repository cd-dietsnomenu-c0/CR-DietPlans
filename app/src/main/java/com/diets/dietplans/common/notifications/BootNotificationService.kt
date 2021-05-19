package com.diets.dietplans.common.notifications

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class BootNotificationService : JobIntentService() {

    companion object{
        private const val JOB_ID = 1

        fun  enqueueWork(context: Context, work: Intent){
            enqueueWork(context, BootNotificationService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {
        ScheduleSetter.setReactAlarm(applicationContext)
        ScheduleSetter.setAlarm(applicationContext)
    }
}
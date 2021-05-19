package com.diets.dietplans.common.notifications

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object ScheduleSetter {

    fun setAlarm(context: Context){
        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (now.after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        val intent = Intent(context, TrackerAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManger = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

        alarmManger.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent)
    }

    fun setReactAlarm(context: Context){
        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (now.after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        val intent = Intent(context, ReactAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManger = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

        alarmManger.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent)
    }
}
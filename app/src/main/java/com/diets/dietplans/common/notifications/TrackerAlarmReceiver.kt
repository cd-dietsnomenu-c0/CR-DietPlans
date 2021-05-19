package com.diets.dietplans.common.notifications

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.diets.dietplans.App
import com.diets.dietplans.R
import com.diets.dietplans.SplashActivity
import com.diets.dietplans.common.DBHolder

class TrackerAlarmReceiver : BroadcastReceiver() {

    companion object {
        private val CHANNEL_ID = "com.weightloss.mydietcoach.channelId"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isHasFood()) {
            val notificationIntent = Intent(context, SplashActivity::class.java)

            var collapsedView = RemoteViews(context?.packageName, R.layout.view_tracker_notification)

            val VIBRATE_PATTERN = longArrayOf(0, 500)
            val NOTIFICATION_COLOR = Color.RED
            val NOTIFICATION_SOUND_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntent(notificationIntent)

            val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context!!, CHANNEL_ID).setCustomBigContentView(collapsedView)
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notification = builder
                    .setAutoCancel(true)
                    .setVibrate(VIBRATE_PATTERN)
                    .setSmallIcon(R.drawable.ic_tracker_notify)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setSound(NOTIFICATION_SOUND_URI)
                    .setContentIntent(pendingIntent).build()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID)
            }

            val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                        CHANNEL_ID,
                        context.resources.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                )
                val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                channel.enableLights(true)
                channel.setSound(alarmSound, audioAttributes)
                channel.lightColor = NOTIFICATION_COLOR
                channel.vibrationPattern = VIBRATE_PATTERN
                channel.enableVibration(true)
                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notification)
        }
    }

    private fun isHasFood(): Boolean {
        return if (App.getInstance().db.dietDAO().getAll().isEmpty()) {
            false
        } else {
            DBHolder.set(App.getInstance().db.dietDAO().getAll()[0])
            !DBHolder.isCompletedYesterday()
        }
    }
}
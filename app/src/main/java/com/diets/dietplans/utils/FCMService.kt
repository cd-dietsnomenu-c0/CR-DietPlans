package com.diets.dietplans.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.diets.dietplans.Activities.MainActivity
import com.diets.dietplans.App
import com.diets.dietplans.BuildConfig
import com.diets.dietplans.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        Log.e("LOL", "etet")

        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


        var pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var collapsedView = RemoteViews(packageName, R.layout.view_notification)
        collapsedView.setTextViewText(R.id.tvNotificationTitle, p0.data["title"])

        var largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification)
        var notificationBuilder = NotificationCompat.Builder(this, "com.diets.dietplans")
                .setSmallIcon(R.drawable.ic_small_notification)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.notification))
                .setVibrate(longArrayOf(0, 500))
                .setLights(Color.MAGENTA, 500, 1000)
                .setContentIntent(pendingIntent)
                .setCustomContentView(collapsedView)
        var notification = notificationBuilder.build()
        var notificationTarget = NotificationTarget(this,
                R.id.ivAvatarNotification, collapsedView,
                notification, 0)
        Handler(Looper.getMainLooper()).post(Runnable {
            Glide.with(App.getContext()).asBitmap().load(p0.data["url"]).into(notificationTarget)
        })

        var notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("com.diets.dietplans",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notification)



    }


}
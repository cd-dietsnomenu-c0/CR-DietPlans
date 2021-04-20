package com.diets.dietplans;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.amplitude.api.Amplitude;

public class App extends MultiDexApplication {

    private static Context context;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        app = this;

        Amplitude
                .getInstance()
                .initialize(this, "e4f1319fda538c72f5981502e0962abb").
                enableForegroundTracking(this);

        Amplitude.getInstance().logEvent("EVENT_NAME_HERE");

    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return app;
    }

    @SuppressLint("NewApi")
    public void createNotificationChannel(@NonNull Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            String channelId = "com.diets.dietplans";
            String channelName = "com.diets.dietplans";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification), att);
            channel.setLightColor(Color.parseColor("#4B8A08"));
            channel.setVibrationPattern(new long[]{0, 500});
            channel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

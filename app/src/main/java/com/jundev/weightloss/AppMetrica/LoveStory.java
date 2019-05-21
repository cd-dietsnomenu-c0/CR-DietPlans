package com.jundev.weightloss.AppMetrica;

import android.app.Application;

import com.amplitude.api.Amplitude;

public class LoveStory extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "be460abcf40b08b38ced8fe42d9c9dc6").
                enableForegroundTracking(this);
    }
}

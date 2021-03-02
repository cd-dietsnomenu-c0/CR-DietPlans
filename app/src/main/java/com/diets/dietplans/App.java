package com.diets.dietplans;

import androidx.multidex.MultiDexApplication;

import com.amplitude.api.Amplitude;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "be460abcf40b08b38ced8fe42d9c9dc6").
                enableForegroundTracking(this);

    }
}

package com.diets.dietplans;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.amplitude.api.Amplitude;
import com.diets.dietplans.R;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "be460abcf40b08b38ced8fe42d9c9dc6").
                enableForegroundTracking(this);

    }
}

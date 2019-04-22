package com.pdc.metronome;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.hawk.Hawk;

public class MainApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}

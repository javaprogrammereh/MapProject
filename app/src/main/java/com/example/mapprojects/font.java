package com.example.mapprojects;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class font extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iranSans4Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}

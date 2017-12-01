package com.lrdwhyt.notepad;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        LocaleHelper.updateLocale(this);
        super.onCreate();
    }

}

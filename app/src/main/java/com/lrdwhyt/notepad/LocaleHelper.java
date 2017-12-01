package com.lrdwhyt.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {

    public static void updateLocale(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String preferredLocale = pref.getString("pref_language", "");
        Locale locale;
        if (!preferredLocale.equals("")) {
            locale = stringToLocale(preferredLocale);
        } else if (!Locale.getDefault().equals(Resources.getSystem().getConfiguration().locale)) {
            locale = Resources.getSystem().getConfiguration().locale;
        } else {
            return;
        }
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static Locale stringToLocale(String localeString) {
        String[] localeArr = localeString.split("_");
        if (localeArr.length == 1) {
            return new Locale(localeString);
        } else if (localeArr.length == 2) {
            return new Locale(localeArr[0], localeArr[1]);
        } else if (localeArr.length == 3) {
            return new Locale(localeArr[0], localeArr[1], localeArr[2]);
        } else {
            throw new RuntimeException("Invalid locale string");
        }
    }

}

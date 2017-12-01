package com.lrdwhyt.notepad.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lrdwhyt.notepad.LocaleHelper;
import com.lrdwhyt.notepad.MainPreferenceFragment;
import com.lrdwhyt.notepad.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences.OnSharedPreferenceChangeListener sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_settings);

        getFragmentManager().beginTransaction().replace(R.id.content, new MainPreferenceFragment()).commit();

        sl = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("pref_language")) {
                    LocaleHelper.updateLocale(SettingsActivity.this);
                    recreate();
                }
            }
        };
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
        s.registerOnSharedPreferenceChangeListener(sl);

    }

    @Override
    public void onBackPressed() {
        if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

}

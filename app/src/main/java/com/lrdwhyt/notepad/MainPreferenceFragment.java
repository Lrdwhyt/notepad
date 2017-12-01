package com.lrdwhyt.notepad;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class MainPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);
        Preference p = findPreference("pref_manage_tags");
        p.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getFragmentManager().beginTransaction().replace(R.id.content, new ManageTagPreferenceFragment()).addToBackStack(ManageTagPreferenceFragment.class.getSimpleName()).commit();
                return false;
            }
        });
    }

}

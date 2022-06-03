package com.lostacos.util.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor prefEditor;

    public static String getPreference(Context context, String key) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, null);
    }

    public static void setPreference(Context context, String key ,String value) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = pref.edit();
        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    public static void deletePreference(Context context, String value) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = pref.edit();
        prefEditor.remove(value);
        prefEditor.commit();
    }

    public static void clearAllPreferences(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = pref.edit();
        prefEditor.clear();
        prefEditor.commit();
    }
}

package com.lostacos.Model;

import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.getPreference;
import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.setPreference;

import android.content.Context;

public class Tables {
    public boolean getTable(Context context, Integer i) {
        return Boolean.parseBoolean(getPreference(context, "table" + i));
    }

    public void setTable(Context context, Integer i, Boolean value) {
        setPreference(context, "table" + i, value.toString());
    }
}

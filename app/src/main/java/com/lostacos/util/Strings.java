package com.lostacos.util;

import android.content.Context;
import android.content.res.Resources;

public class Strings {
    public static String getStringByName(Context context, String stringName) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(stringName, "string", context.getPackageName()));
    }
}

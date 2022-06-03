package com.lostacos.controller;

import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.lostacos.R;
import com.lostacos.view.LoginActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageController{
    public static void getLocale(Context context){
        String localeName = getPreference(context, "language");
        if(localeName == null){
            localeName = "pt";
            setPreference(context, "language", localeName);
        }
        Locale locale = new Locale(localeName);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
    public static void setLocale(Context context, String currentLanguage, String localeName){
        if(!localeName.equals(currentLanguage)) {
            Locale locale = new Locale(localeName);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            setPreference(context, "language", localeName);
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }
    public static void SelectLanguage(Context context){
        Spinner spinner;
        String currentLanguage;
        String[] languages = {"pt", "es", "en"};

        if(getPreference(context, "language") != null){
            currentLanguage = getPreference(context, "language");
        }else{
            currentLanguage = "pt";
        }

        spinner= ((Activity)context).findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();
        list.add(currentLanguage);
        for(int i=0; i<languages.length; i++){
            if(!languages[i].equals(currentLanguage)){
                list.add(languages[i]);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(String.valueOf(spinner.getAdapter().getItem(position)).equals("en")){
                    setLocale(context, currentLanguage, "en");
                }else if(String.valueOf(spinner.getAdapter().getItem(position)).equals("es")){
                    setLocale(context, currentLanguage, "es");
                }else{
                    setLocale(context, currentLanguage, "pt");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}

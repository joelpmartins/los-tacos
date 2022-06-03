package com.lostacos.util;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DataFormat {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String setFormatDateInString(String date) {
        Date c_date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            c_date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return setFormatDate(c_date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String setFormatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDay(Integer a){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, a);
        calendar.add(Calendar.HOUR, 3); // 3 horas para compensar a diferença de horário do Heroku para teste
        Date day = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(day);
    }
}

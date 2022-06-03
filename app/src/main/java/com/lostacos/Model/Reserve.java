package com.lostacos.Model;

import static com.lostacos.util.shared_preferences.SharedPreferencesUtil.*;

import android.content.Context;

public class Reserve {
    private String date;
    private String hour;
    private String people;
    private Integer table;

    public Integer getTable() {
        return table;
    }

    public void setTable(Context context, Integer table) {
        this.table = table;
        setPreference(context, "table", table.toString());
    }

    public String getDate() {
        return date;
    }

    public void setDate(Context context, String date) {
        this.date = date;
        setPreference(context, "date" , date);
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}

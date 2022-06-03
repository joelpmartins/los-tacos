package com.lostacos.Model;

public class MyReserves {
    private String id;
    private String date;
    private String hour;
    private String people;

    public MyReserves(String id, String date, String hour, String people) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.people = people;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

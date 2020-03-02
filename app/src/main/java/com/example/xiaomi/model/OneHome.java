package com.example.xiaomi.model;

public class OneHome {
    private  String saying;
    private  String from ;
    private  String date ;
    private  String time ;

    public String getSaying() {
        return saying;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

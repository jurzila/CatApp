package com.example.catapp3.model;

import java.util.Date;

public class Feeder {

    private Double calories;
    private String dateFed;
    private String timeFed;
    private String whoFed;

    public Feeder(Double cal, String date, String time, String whoFed){
        this.calories = cal;
        this.dateFed = date;
        this.timeFed = time;
        this.whoFed = whoFed;

    }

    public Double getCalories() {
        return calories;
    }

    public String getDateFed() {
        return dateFed;
    }

    public String getWhoFed() {
        return whoFed;
    }

    public String getTimeFed() { return timeFed;
    }
}

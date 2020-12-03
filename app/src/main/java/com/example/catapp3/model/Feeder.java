package com.example.catapp3.model;

import java.util.Date;

public class Feeder {

    private Double calories;
    private Date timeFed;
    private String whoFed;

    public Feeder(Double cal, Date time, String whoFed){
        this.calories = cal;
        this.timeFed = time;
        this.whoFed = whoFed;

    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Date getTimeFed() {
        return timeFed;
    }

    public void setTimeFed(Date timeFed) {
        this.timeFed = timeFed;
    }

    public String getWhoFed() {
        return whoFed;
    }

    public void setWhoFed(String whoFed) {
        this.whoFed = whoFed;
    }
}

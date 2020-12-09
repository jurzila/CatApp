package com.example.catapp3.model;

import android.graphics.Bitmap;

import java.util.Date;

public class Cat {

    private String name;
    private String sex;
    private String breed;
    private String birthday;
    private Double weight;
    private int catId;
    private int userId;
    private Bitmap picture;

    public Cat(String name, String sex, String breed, String birthday, Double weight, int catId, int userId, Bitmap picture) {
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.birthday = birthday;
        this.weight = weight;
        this.catId = catId;
        this.userId = userId;
        this.picture = picture;
    }

    public Cat(String name, String birthday, int userId, Bitmap picture) {
        this.name = name;
        this.birthday = birthday;
        this.userId = userId;
        this.picture = picture;

    }

    public int getCatId() {
        return catId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBreed() {
        return breed;
    }

    public String getBirthday() {
        return birthday;
    }

    public Bitmap getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return ""+getName();
    }
}

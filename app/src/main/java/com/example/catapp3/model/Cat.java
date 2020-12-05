package com.example.catapp3.model;

import java.util.Date;

public class Cat {

    private String name;
    private String sex;
    private String breed;
    private String birthday;
    private Double weight;
    private int userId;

    public Cat(String name, String sex, String breed, String birthday, Double weight, int userId) {
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.birthday = birthday;
        this.weight = weight;
        this.userId = userId;
    }

    public Cat(String name, String sex, String breed, String birthday) {
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.birthday = birthday;

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


}

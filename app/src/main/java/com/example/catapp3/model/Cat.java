package com.example.catapp3.model;

public class Cat {

    private String name;
    private String sex;
    private String breed;
    private String birthday;
    private Double weight;

    public Cat(String name, String sex, String breed, String birthday, Double weight) {
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.birthday = birthday;
        this.weight = weight;
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

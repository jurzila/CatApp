package com.example.catapp3.model;

public class User {

    private Integer usernameId;
    private String username;
    private String password;
    private String email;
    private Cat cat;

    public User(String username, String password, String email) {
        this.usernameId = usernameId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Cat getCat() {
        return cat;
    }
    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public String getUserName() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public Integer getUsernameId() {
        return usernameId;
    }
}

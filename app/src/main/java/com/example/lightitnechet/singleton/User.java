package com.example.lightitnechet.singleton;

public class User {
    private String name;
    private String lastName;
    private String imagePath;
    private String token;
    private String password;

    private static User instance ;

    public static synchronized User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    private User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


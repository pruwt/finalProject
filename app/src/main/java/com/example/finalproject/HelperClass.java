package com.example.finalproject;

public class HelperClass {

    String name, email, password, farmlocation;

    public HelperClass(String name, String email, String password, String farmlocation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.farmlocation = farmlocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFarmlocation() {
        return farmlocation;
    }

    public void setFarmlocation(String farmlocation) {
        this.farmlocation = farmlocation;
    }

    public HelperClass() {
    }
}

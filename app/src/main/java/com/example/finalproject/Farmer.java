package com.example.finalproject;

public class Farmer {

    private String id;
    private String name;
    private String email;
    private String location;
    private String role;

    public Farmer(String id, String name, String email, String location, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getRole() {
        return role;
    }
}

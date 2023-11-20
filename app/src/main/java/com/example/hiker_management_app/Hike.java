package com.example.hiker_management_app;

public class Hike {
    private int id;
    private String name;
    private String location;
    private String date;
    // Add other fields like length, difficulty, etc.
    private boolean parking;
    private String length;
    private String difficulty;
    private String description;
    private int NumOfParticipants;

    // Constructor
    public Hike(int id, String name, String location, String date, Boolean parking, String difficulty, String length, String description, int NumOfParticipants) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.difficulty = difficulty;
        this.length = length;
        this.description = description;
        this.NumOfParticipants = NumOfParticipants;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfParticipants() {
        return NumOfParticipants;
    }

    public void setNumOfParticipants(int numOfParticipants) {
        NumOfParticipants = numOfParticipants;
    }
}


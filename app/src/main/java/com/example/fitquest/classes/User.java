package com.example.fitquest.classes;

import java.util.List;

public class User {

    private String userId;
    private String username;
    private int age;
    private double weight;
    private double height;
    private int fitnessGoal;
    private int activityLevel;
    private String currentProgramId;
    private String date;

    public User() {
    }

    public User(String userId, String username, int age, double weight, double height,
                int fitnessGoal, int activityLevel, String currentProgramId, String date) {
        this.userId = userId;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.fitnessGoal = fitnessGoal;
        this.activityLevel = activityLevel;
        this.currentProgramId = currentProgramId;
        this.date = date;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public int getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(int fitnessGoal) { this.fitnessGoal = fitnessGoal; }

    public int getActivityLevel() { return activityLevel; }
    public void setActivityLevel(int activityLevel) { this.activityLevel = activityLevel; }

    public String getCurrentProgramId() { return currentProgramId; }
    public void setCurrentProgramId(String currentProgramId) { this.currentProgramId = currentProgramId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
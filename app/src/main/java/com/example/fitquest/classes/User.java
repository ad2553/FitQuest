package com.example.fitquest.classes;

public class User {

    private String username;
    private int age;
    private double weight;
    private double height;
    private String fitnessGoal;
    private String activityLevel;

    // חובה לFirebase
    public User() {
    }

    public User(String username, int age, double weight, double height,
                String fitnessGoal, String activityLevel) {

        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.fitnessGoal = fitnessGoal;
        this.activityLevel = activityLevel;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }
}
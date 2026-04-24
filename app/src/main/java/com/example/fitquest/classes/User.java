package com.example.fitquest.classes;

import java.util.HashMap;
import java.util.Map;

/**
 * מחלקה המייצגת משתמש במערכת FitQuest.
 * האובייקט נשמר ב-Firebase Realtime Database ומכיל את כל המדדים הפיזיים
 * והתקדמות השרירים של המשתמש.
 */
public class User {

    /** מזהה ייחודי של המשתמש ב-Firebase Auth */
    private String uid;
    /** שם המשתמש כפי שיוצג בפרופיל */
    private String username;
    /** כתובת האימייל של המשתמש */
    private String email;
    /** קישור לתמונת הפרופיל המאוחסנת ב-Firebase Storage */
    private String profilePictureUrl;
    /** גיל המשתמש בשנים */
    private int age;
    /** משקל המשתמש בקילוגרמים */
    private double weight;
    /** גובה המשתמש בסנטימטרים */
    private double height;
    /** מטרת הכושר של המשתמש (למשל: Build Muscle) */
    private String fitnessGoal;
    /** רמת פעילות כללית (1-5) */
    private int activityLevel;
    /** מספר האימונים הכולל שהמשתמש השלים */
    private int totalWorkoutsCompleted;
    /** תאריך יצירת החשבון בפורמט ISO-8601 */
    private String createdAt;
    /** מפת קבוצות השרירים של המשתמש (המפתח הוא מזהה השריר) */
    private Map<String, MuscleGroup> muscleGroups;

    public User() {}

    public User(String uid, String username, String email,
                int age, double weight, double height,
                String fitnessGoal, int activityLevel, String createdAt) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.fitnessGoal = fitnessGoal;
        this.activityLevel = activityLevel;
        this.totalWorkoutsCompleted = 0;
        this.createdAt = createdAt;
        this.muscleGroups = buildDefaultMuscleGroups();
    }

    private Map<String, MuscleGroup> buildDefaultMuscleGroups() {
        Map<String, MuscleGroup> groups = new HashMap<>();
        groups.put("chest",     new MuscleGroup("chest",     "Chest"));
        groups.put("back",      new MuscleGroup("back",      "Back"));
        groups.put("legs",      new MuscleGroup("legs",      "Legs"));
        groups.put("shoulders", new MuscleGroup("shoulders", "Shoulders"));
        groups.put("arms",      new MuscleGroup("arms",      "Arms"));
        groups.put("core",      new MuscleGroup("core",      "Core"));
        return groups;
    }

    // Getters and Setters (omitted for brevity in display, but present in file)
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public String getFitnessGoal() { return fitnessGoal; }
    public void setFitnessGoal(String fitnessGoal) { this.fitnessGoal = fitnessGoal; }
    public int getActivityLevel() { return activityLevel; }
    public void setActivityLevel(int activityLevel) { this.activityLevel = activityLevel; }
    public int getTotalWorkoutsCompleted() { return totalWorkoutsCompleted; }
    public void setTotalWorkoutsCompleted(int totalWorkoutsCompleted) { this.totalWorkoutsCompleted = totalWorkoutsCompleted; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public Map<String, MuscleGroup> getMuscleGroups() { return muscleGroups; }
    public void setMuscleGroups(Map<String, MuscleGroup> muscleGroups) { this.muscleGroups = muscleGroups; }
}
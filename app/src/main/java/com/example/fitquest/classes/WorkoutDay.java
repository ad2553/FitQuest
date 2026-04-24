package com.example.fitquest.classes;

import java.util.Map;

public class WorkoutDay {

    private String dayId;
    private String date;
    private int estimate;
    private boolean workoutDone;
    private Map<String, Exercise> exercises;
//להוסיף פעולת השוואה
    public WorkoutDay() {
    }

    public WorkoutDay(String dayId, String date, int estimate,
                      boolean workoutDone, Map<String, Exercise> exercises) {
        this.dayId = dayId;
        this.date = date;
        this.estimate = estimate;
        this.workoutDone = workoutDone;
        this.exercises = exercises;
    }

    public String getDayId() { return dayId; }
    public void setDayId(String dayId) { this.dayId = dayId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getEstimate() { return estimate; }
    public void setEstimate(int estimate) { this.estimate = estimate; }

    public boolean isWorkoutDone() { return workoutDone; }
    public void setWorkoutDone(boolean workoutDone) { this.workoutDone = workoutDone; }

    public Map<String, Exercise> getExercises() { return exercises; }
    public void setExercises(Map<String, Exercise> exercises) { this.exercises = exercises; }
}
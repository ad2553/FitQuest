package com.example.fitquest.classes;

import java.util.Map;

public class WorkoutProgram {

    private String programId;
    private String programName;
    private String date;
    private int level;
    private boolean isActive;
    private Map<String, WorkoutDay> days;

    public WorkoutProgram() {
    }

    public WorkoutProgram(String programId, String programName, String date,
                          int level, boolean isActive, Map<String, WorkoutDay> days) {
        this.programId = programId;
        this.programName = programName;
        this.date = date;
        this.level = level;
        this.isActive = isActive;
        this.days = days;
    }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Map<String, WorkoutDay> getDays() { return days; }
    public void setDays(Map<String, WorkoutDay> days) { this.days = days; }
}
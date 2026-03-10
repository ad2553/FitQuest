package com.example.fitquest.classes;

import java.util.List;

public class WorkoutProgram {

    private String programId;
    private String programName;
    private String date;
    private int level;
    private boolean isActive;
    private List<WorkoutDay> days;

    public WorkoutProgram() {
    }

    public WorkoutProgram(String programId, String programName, String date,
                          int level, boolean isActive, List<WorkoutDay> days) {
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

    public List<WorkoutDay> getDays() { return days; }
    public void setDays(List<WorkoutDay> days) { this.days = days; }
}
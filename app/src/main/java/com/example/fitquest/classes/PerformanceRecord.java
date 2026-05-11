package com.example.fitquest.classes;

/**
 * מחלקה המייצגת מעקב ורישום של ביצוע תרגיל (היסטוריה).
 * משמשת להשוואה בין כמות הסטים והחזרות שתוכננו לבין מה שבוצע בפועל.
 */
public class PerformanceRecord {

    private String recordId;
    private String programId;
    private String exerciseId;
    private String muscleGroupId;  // לשימוש האלגוריתם לחישוב ביצועי שריר
    private String date;

    private int plannedSets;
    private int plannedReps;

    private int actualSets;
    private int actualReps;

    private boolean completed;

    public PerformanceRecord() {
    }

    public PerformanceRecord(String recordId, String programId, String exerciseId,
                             String muscleGroupId, String date,
                             int plannedSets, int plannedReps,
                             int actualSets, int actualReps, boolean completed) {
        this.recordId = recordId;
        this.programId = programId;
        this.exerciseId = exerciseId;
        this.muscleGroupId = muscleGroupId;
        this.date = date;
        this.plannedSets = plannedSets;
        this.plannedReps = plannedReps;
        this.actualSets = actualSets;
        this.actualReps = actualReps;
        this.completed = completed;
    }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getExerciseId() { return exerciseId; }
    public void setExerciseId(String exerciseId) { this.exerciseId = exerciseId; }

    public String getMuscleGroupId() { return muscleGroupId; }
    public void setMuscleGroupId(String muscleGroupId) { this.muscleGroupId = muscleGroupId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getPlannedSets() { return plannedSets; }
    public void setPlannedSets(int plannedSets) { this.plannedSets = plannedSets; }

    public int getPlannedReps() { return plannedReps; }
    public void setPlannedReps(int plannedReps) { this.plannedReps = plannedReps; }

    public int getActualSets() { return actualSets; }
    public void setActualSets(int actualSets) { this.actualSets = actualSets; }

    public int getActualReps() { return actualReps; }
    public void setActualReps(int actualReps) { this.actualReps = actualReps; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
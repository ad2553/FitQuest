package com.example.fitquest.classes;

/**
 * מחלקה המייצגת תרגיל בסיסי במערכת.
 * מכילה את פרטי התרגיל, הקושי, קבוצת השרירים אליה הוא שייך והגדרות העבודה (סטים וחזרות).
 */
public class Exercise {

    private String exerciseId;
    private String exerciseName;
    private String description;
    private String muscleGroup;
    private String difficulty;
    private int sets;
    private int reps;
    private int restTime;

    public Exercise() {
    }

    public Exercise(String exerciseId, String exerciseName, String description,
                    String muscleGroup, String difficulty, int sets,
                    int reps, int restTime) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.description = description;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
    }

    public String getExerciseId() { return exerciseId; }
    public void setExerciseId(String exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getRestTime() { return restTime; }
    public void setRestTime(int restTime) { this.restTime = restTime; }
}
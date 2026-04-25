package com.example.fitquest.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitquest.FBRef.refAuth;
import static com.example.fitquest.FBRef.refUsers;

import android.util.Patterns;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitquest.R;
import com.example.fitquest.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * אקטיביטי האחראית על רישום משתמשים חדשים.
 * אוספת נתונים פיזיים (גיל, משקל, גובה) ומטרות כושר, יוצרת חשבון ב-Firebase Auth
 * ושומרת את פרופיל המשתמש ב-Realtime Database.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText eTUsername, eTEmail, eTPass, eTAge, eTWeight, eTHeight, eTActivity;
    /** רשימת בחירה למטרת הכושר כדי להבטיח קלט תקין עבור ה-AI */
    private Spinner spinnerGoal;
    private TextView tVMsg;
    /** רשימת האפשרויות המוצגות ב-Spinner */
    private final String[] goals = {"Build Muscle", "Lose Weight", "Stay Fit", "Powerlifting", "Endurance"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // אתחול רכיבי הממשק
        eTUsername = findViewById(R.id.usernameInput);
        eTEmail    = findViewById(R.id.emailInput);
        eTPass     = findViewById(R.id.passwordInput);
        eTAge      = findViewById(R.id.ageInput);
        eTWeight   = findViewById(R.id.weightInput);
        eTHeight   = findViewById(R.id.heightInput);
        spinnerGoal = findViewById(R.id.goalSpinner);
        eTActivity = findViewById(R.id.activityInput);
        tVMsg      = findViewById(R.id.msgText);

        // הגדרת ה-Adapter עבור ה-Spinner של מטרות הכושר
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(adapter);
    }

    /**
     * פונקציה המופעלת בעת לחיצה על כפתור "Register".
     * מבצעת יצירת משתמש ב-Firebase ומאחסנת את הנתונים שלו.
     */
    public void createUser(View view) {
        // בדיקה מקיפה של כל שדות הקלט
        if (!validateInput()) {
            return;
        }

        String email = eTEmail.getText().toString().trim();
        String pass  = eTPass.getText().toString();

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Connecting");
        pd.setMessage("Creating user...");
        pd.show();

        // יצירת משתמש ב-Firebase Authentication
        refAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pd.dismiss();

                        if (task.isSuccessful()) {
                            // המשתמש נוצר בהצלחה - שמירת נתוני הפרופיל שלו
                            FirebaseUser firebaseUser = refAuth.getCurrentUser();
                            String uid = firebaseUser.getUid();

                            String createdAt = new SimpleDateFormat(
                                    "yyyy-MM-dd", Locale.getDefault()
                            ).format(new Date());

                            // יצירת אובייקט משתמש עם הנתונים הפיזיים
                            User newUser = new User(
                                    uid,
                                    eTUsername.getText().toString().trim(),
                                    email,
                                    Integer.parseInt(eTAge.getText().toString()),
                                    Double.parseDouble(eTWeight.getText().toString()),
                                    Double.parseDouble(eTHeight.getText().toString()),
                                    spinnerGoal.getSelectedItem().toString(),
                                    Integer.parseInt(eTActivity.getText().toString()),
                                    createdAt
                            );

                            // שמירה ב-Database תחת נתיב users/{uid}
                            refUsers.child(uid).setValue(newUser);

                            // מעבר ישיר לבית
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            // טיפול בשגיאות נפוצות (סיסמה חלשה, משתמש קיים וכו')
                            Exception exp = task.getException();
                            if (exp instanceof FirebaseAuthWeakPasswordException) {
                                tVMsg.setText("Password too weak");
                            } else if (exp instanceof FirebaseAuthUserCollisionException) {
                                tVMsg.setText("User already exists");
                            } else {
                                tVMsg.setText("Error creating user");
                            }
                        }
                    }
                });
    }

    /**
     * מבצעת וולידציה מקיפה לכל שדות ההרשמה.
     * @return true אם כל השדות תקינים, false אחרת.
     */
    private boolean validateInput() {
        boolean isValid = true;

        String username = eTUsername.getText().toString().trim();
        String email = eTEmail.getText().toString().trim();
        String pass = eTPass.getText().toString();
        String ageStr = eTAge.getText().toString().trim();
        String weightStr = eTWeight.getText().toString().trim();
        String heightStr = eTHeight.getText().toString().trim();
        String activityStr = eTActivity.getText().toString().trim();

        // בדיקת שם משתמש
        if (username.isEmpty()) {
            eTUsername.setError("Username is required");
            isValid = false;
        } else if (username.length() < 3) {
            eTUsername.setError("Username must be at least 3 characters");
            isValid = false;
        } else if (username.length() > 20) {
            eTUsername.setError("Username must be at most 20 characters");
            isValid = false;
        }

        // בדיקת אימייל
        if (email.isEmpty()) {
            eTEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eTEmail.setError("Please enter a valid email");
            isValid = false;
        }

        // בדיקת סיסמה
        if (pass.isEmpty()) {
            eTPass.setError("Password is required");
            isValid = false;
        } else if (pass.length() < 6) {
            eTPass.setError("Password must be at least 6 characters");
            isValid = false;
        } else if (pass.length() > 20) {
            eTPass.setError("Password must be at most 20 characters");
            isValid = false;
        }

        // בדיקת גיל
        if (ageStr.isEmpty()) {
            eTAge.setError("Age is required");
            isValid = false;
        } else {
            try {
                int age = Integer.parseInt(ageStr);
                if (age < 5 || age > 120) {
                    eTAge.setError("Age must be between 5 and 120");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                eTAge.setError("Please enter a valid number");
                isValid = false;
            }
        }

        // בדיקת משקל
        if (weightStr.isEmpty()) {
            eTWeight.setError("Weight is required");
            isValid = false;
        } else {
            try {
                double weight = Double.parseDouble(weightStr);
                if (weight < 20 || weight > 400) {
                    eTWeight.setError("Weight must be between 20 and 400 kg");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                eTWeight.setError("Please enter a valid number");
                isValid = false;
            }
        }

        // בדיקת גובה
        if (heightStr.isEmpty()) {
            eTHeight.setError("Height is required");
            isValid = false;
        } else {
            try {
                double height = Double.parseDouble(heightStr);
                if (height < 50 || height > 250) {
                    eTHeight.setError("Height must be between 50 and 250 cm");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                eTHeight.setError("Please enter a valid number");
                isValid = false;
            }
        }

        // בדיקת רמת פעילות
        if (activityStr.isEmpty()) {
            eTActivity.setError("Activity level is required");
            isValid = false;
        } else {
            try {
                int activity = Integer.parseInt(activityStr);
                if (activity < 1 || activity > 7) {
                    eTActivity.setError("Activity must be between 1 and 7 days");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                eTActivity.setError("Please enter a valid number");
                isValid = false;
            }
        }

        if (!isValid) {
            tVMsg.setText("Please correct the errors in the form");
            Toast.makeText(this, "Please check the highlighted fields", Toast.LENGTH_SHORT).show();
        } else {
            tVMsg.setText("");
        }

        return isValid;
    }

    /** מעבר למסך ההתחברות */
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
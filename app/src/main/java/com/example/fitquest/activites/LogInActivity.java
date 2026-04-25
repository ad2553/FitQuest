package com.example.fitquest.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitquest.FBRef.refAuth;

import android.util.Patterns;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitquest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * אקטיביטי המנהלת את תהליך ההתחברות (Login) של המשתמש.
 * כוללת אפשרות ל-"Stay Connected" (זכור אותי) באמצעות SharedPreferences.
 */
public class LogInActivity extends AppCompatActivity {

    private EditText eTEmail, eTPass;
    private TextView tVMsg;
    private CheckBox cBStay;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eTEmail = findViewById(R.id.emailInput);
        eTPass  = findViewById(R.id.passwordInput);
        tVMsg   = findViewById(R.id.msgText);
        cBStay  = findViewById(R.id.stayConnectedCb);

        // טעינת הגדרות ה-"זכור אותי"
        settings = getSharedPreferences("RemeberMe", MODE_PRIVATE);
        boolean stay = settings.getBoolean("stayConnect", false);

        if (stay) {
            String email = settings.getString("email", "");
            eTEmail.setText(email);
            cBStay.setChecked(true);
        }
    }

    /**
     * פונקציה המופעלת בעת לחיצה על כפתור "Log In".
     * מבצעת אימות מול Firebase Auth.
     */
    public void loginUser(View view) {
        String email = eTEmail.getText().toString().trim();
        String pass  = eTPass.getText().toString();

        if (!validateInput(email, pass)) {
            return;
        }

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Connecting");
        pd.setMessage("Authenticating...");
        pd.show();

        refAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();

                        if (task.isSuccessful()) {
                            // התחברות הצליחה - שמירת הגדרות במידה וסומן "זכור אותי"
                            SharedPreferences.Editor editor = settings.edit();
                            if (cBStay.isChecked()) {
                                editor.putBoolean("stayConnect", true);
                                editor.putString("email", email);
                            } else {
                                editor.putBoolean("stayConnect", false);
                            }
                            editor.apply();

                            // מעבר למסך הבית
                            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            tVMsg.setText("Login failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * מבצעת וולידציה לקלט המשתמש.
     * @return true אם הקלט תקין, false אחרת.
     */
    private boolean validateInput(String email, String pass) {
        boolean isValid = true;

        if (email.isEmpty()) {
            eTEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eTEmail.setError("Please enter a valid email");
            isValid = false;
        }

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

        if (!isValid) {
            tVMsg.setText("Please correct the errors above");
            Toast.makeText(this, "Invalid login details", Toast.LENGTH_SHORT).show();
        } else {
            tVMsg.setText(""); // Clear message if valid
        }

        return isValid;
    }

    /** מעבר למסך ההרשמה */
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
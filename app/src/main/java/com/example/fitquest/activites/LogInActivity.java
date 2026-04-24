package com.example.fitquest.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitquest.FBRef.refAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
            String pass  = settings.getString("pass", "");
            eTEmail.setText(email);
            eTPass.setText(pass);
            cBStay.setChecked(true);
        }
    }

    /**
     * פונקציה המופעלת בעת לחיצה על כפתור "Log In".
     * מבצעת אימות מול Firebase Auth.
     */
    public void login(View view) {
        String email = eTEmail.getText().toString().trim();
        String pass  = eTPass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            tVMsg.setText("Please fill all fields");
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
                                editor.putString("pass", pass);
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

    /** מעבר למסך ההרשמה */
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
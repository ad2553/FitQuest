package com.example.fitquest.activites;

import static com.example.fitquest.FBRef.refAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitquest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Activity for user login.
 * Handles authentication via Firebase and "Remember Me" functionality.
 */
public class LogInActivity extends AppCompatActivity {
    /** EditText for email input. */
    private EditText eTEmail;
    /** EditText for password input. */
    private EditText eTPass;
    /** CheckBox for "Remember Me" option. */
    private CheckBox cBStay;

    /**
     * Initializes the activity, sets the content view, and binds the UI components.
     * Checks if the user should be automatically logged in based on SharedPreferences.
     * @param savedInstanceState Bundle containing activity state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eTEmail = findViewById(R.id.emailInput);
        eTPass = findViewById(R.id.passwordInput);
        cBStay = findViewById(R.id.checkBox);

        SharedPreferences settings = getSharedPreferences("RemeberMe", MODE_PRIVATE);
        boolean stayConnect = settings.getBoolean("stayConnect", false);


    }

    /**
     * Attempts to log in the user with the provided email and password.
     * If successful, saves login preference and navigates to the main activity.
     * @param view The clicked view (login button).
     */
    public void loginUser(View view) {
        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Logging in...");
            pd.show();

            refAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                SharedPreferences settings = getSharedPreferences("RemeberMe", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("stayConnect", cBStay.isChecked());
                                editor.apply();

                            } else {
                                Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Navigates the user to the sign-up activity.
     * @param view The clicked view.
     */
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
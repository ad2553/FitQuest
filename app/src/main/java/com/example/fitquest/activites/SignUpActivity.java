package com.example.fitquest.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitquest.FBRef.refAuth;
import static com.example.fitquest.FBRef.refUsers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignUpActivity extends AppCompatActivity {

    private EditText eTUsername, eTEmail, eTPass, eTAge, eTWeight, eTHeight, eTGoal, eTActivity;
    private TextView tVMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eTUsername = findViewById(R.id.usernameInput);
        eTEmail    = findViewById(R.id.emailInput);
        eTPass     = findViewById(R.id.passwordInput);
        eTAge      = findViewById(R.id.ageInput);
        eTWeight   = findViewById(R.id.weightInput);
        eTHeight   = findViewById(R.id.heightInput);
        eTGoal     = findViewById(R.id.goalInput);
        eTActivity = findViewById(R.id.activityInput);
        tVMsg      = findViewById(R.id.msgText);
    }

    public void createUser(View view) {

        String email = eTEmail.getText().toString().trim();
        String pass  = eTPass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            tVMsg.setText("Please fill all required fields");
            return;
        }

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Connecting");
        pd.setMessage("Creating user...");
        pd.show();

        refAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pd.dismiss();

                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = refAuth.getCurrentUser();
                            String uid = firebaseUser.getUid();

                            String createdAt = new SimpleDateFormat(
                                    "yyyy-MM-dd", Locale.getDefault()
                            ).format(new Date());

                            User newUser = new User(
                                    uid,
                                    eTUsername.getText().toString().trim(),
                                    email,
                                    Integer.parseInt(eTAge.getText().toString()),
                                    Double.parseDouble(eTWeight.getText().toString()),
                                    Double.parseDouble(eTHeight.getText().toString()),
                                    eTGoal.getText().toString().trim(),
                                    Integer.parseInt(eTActivity.getText().toString()),
                                    createdAt
                            );

                            // שמירה ב-Firebase תחת users/{uid}
                            refUsers.child(uid).setValue(newUser);

                            // מעבר ישיר לבית — לא חוזרים למסך ההרשמה
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {

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
    /** כפתור "Log In" בתחתית מסך ההרשמה */
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
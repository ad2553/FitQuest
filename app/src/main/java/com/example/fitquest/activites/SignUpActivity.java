package com.example.fitquest.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.fitquest.FBRef.refAuth;
import static com.example.fitquest.FBRef.refData;
import static com.example.fitquest.classes.User.*;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitquest.R;
import com.example.fitquest.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText eTUsername, eTEmail, eTPass, eTAge, eTWeight, eTHeight, eTGoal, eTActivity;
    private TextView tVMsg;

    private FirebaseAuth refAuth;
    private DatabaseReference refDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eTUsername = findViewById(R.id.usernameInput);
        eTEmail = findViewById(R.id.emailInput);
        eTPass = findViewById(R.id.passwordInput);
        eTAge = findViewById(R.id.ageInput);
        eTWeight = findViewById(R.id.weightInput);
        eTHeight = findViewById(R.id.heightInput);
        eTGoal = findViewById(R.id.goalInput);
        eTActivity = findViewById(R.id.activityInput);

        tVMsg = findViewById(R.id.msgText);


        refAuth = FirebaseAuth.getInstance();
        refDB = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void createUser(View view) {

        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();

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

                            FirebaseUser user = refAuth.getCurrentUser();
                            String uid = user.getUid();

                            User newUser = new User(
                                    eTUsername.getText().toString(),
                                    Integer.parseInt(eTAge.getText().toString()),
                                    Double.parseDouble(eTWeight.getText().toString()),
                                    Double.parseDouble(eTHeight.getText().toString()),
                                    eTGoal.getText().toString(),
                                    eTActivity.getText().toString()
                            );

                            refDB.child(uid).setValue(newUser);

                            tVMsg.setText("User created successfully");

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
}
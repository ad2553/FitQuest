package com.example.fitquest.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitquest.R;
/**
 * האקטיביטי הראשונה שמוצגת לאחר מסך הפתיחה (עבור משתמשים שאינם מחוברים).
 * משמשת כמסך שער (Welcome Screen) ומאפשרת ניווט למסך ההתחברות או למסך ההרשמה.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToLogInActivity(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
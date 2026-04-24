package com.example.fitquest.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.fitquest.R;
import com.example.fitquest.fragments.CreditsFragment;
import com.example.fitquest.fragments.HistoryFragment;
import com.example.fitquest.fragments.HomeFragment;
import com.example.fitquest.fragments.ProfileFragment;
import com.example.fitquest.fragments.ProgressFragment;
import com.example.fitquest.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.fitquest.receivers.NetworkReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;
import android.graphics.Color;

import static com.example.fitquest.FBRef.refAuth;

/**
 * האקטיביטי המרכזית של האפליקציה (Master Activity).
 * מנהלת את ה-Toolbar העליון, ה-Bottom Navigation ואת החלפת ה-Fragments השונים.
 * בנוסף, מאזינה לשינויים בסטטוס הרשת באמצעות BroadcastReceiver.
 */
public class HomeActivity extends AppCompatActivity implements NetworkReceiver.NetworkListener {

    /** רסיבר המאזין לשינויי קישוריות רשת */
    private NetworkReceiver networkReceiver;
    /** הודעת Snackbar המוצגת כאשר אין חיבור אינטרנט */
    private Snackbar noInternetSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // הגדרת ה-Toolbar העליון
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // הגדרת הניווט התחתון
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // טעינת מסך הבית (המשימות של היום) כברירת מחדל
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment()).commit();
        }

        // הגדרת מאזין ללחיצות על כפתורי הניווט התחתון
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            } else if (itemId == R.id.nav_progress) {
                selectedFragment = new ProgressFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            // ביצוע החלפת ה-Fragment בתוך ה-Container
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment).commit();
                return true;
            }
            return false;
        });

        // אתחול הרסיבר לבדיקת רשת
        networkReceiver = new NetworkReceiver(this);
    }

    /**
     * פונקציית Callback המופעלת כאשר סטטוס הרשת משתנה.
     * 
     * @param isConnected True אם יש חיבור אינטרנט, False אחרת.
     */
    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (!isConnected) {
            if (noInternetSnackbar == null) {
                noInternetSnackbar = Snackbar.make(findViewById(R.id.fragmentContainer), 
                        "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                noInternetSnackbar.setBackgroundTint(Color.RED);
                noInternetSnackbar.setTextColor(Color.WHITE);
            }
            if (!noInternetSnackbar.isShown()) {
                noInternetSnackbar.show();
            }
        } else {
            if (noInternetSnackbar != null && noInternetSnackbar.isShown()) {
                noInternetSnackbar.dismiss();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // רישום הרסיבר לקבלת עדכוני רשת
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ביטול רישום הרסיבר
        unregisterReceiver(networkReceiver);
    }

    /**
     * יוצר את תפריט האפשרויות ב-Toolbar (Settings, Credits, Logout).
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * מטפל בלחיצות על פריטי התפריט ב-Toolbar.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_settings) {
            switchFragment(new SettingsFragment());
            return true;
        } else if (itemId == R.id.menu_credits) {
            switchFragment(new CreditsFragment());
            return true;
        } else if (itemId == R.id.menu_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * מחליף את ה-Fragment הנוכחי באחד חדש ומוסיף אותו ל-BackStack.
     */
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * מבצע התנתקות מהמערכת ומנקה את הגדרות ה-"זכור אותי".
     */
    private void logout() {
        refAuth.signOut();

        SharedPreferences settings = getSharedPreferences("RemeberMe", MODE_PRIVATE);
        settings.edit().putBoolean("stayConnect", false).apply();

        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
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
import com.example.fitquest.fragments.HistoryFragment;
import com.example.fitquest.fragments.HomeFragment;
import com.example.fitquest.fragments.ProfileFragment;
import com.example.fitquest.fragments.ProgressFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.fitquest.receivers.NetworkReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;
import android.graphics.Color;

import static com.example.fitquest.FBRef.refAuth;

public class HomeActivity extends AppCompatActivity implements NetworkReceiver.NetworkListener {

    private NetworkReceiver networkReceiver;
    private Snackbar noInternetSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // הגדרת Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // הגדרת BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // הצגת המסך הראשון (Today's Tasks) בטעינה הראשונית
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment()).commit();
        }

        // ניווט בין ה-Fragments דרך הלשוניות
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

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment).commit();
                return true;
            }
            return false;
        });

        // אתחול ה-NetworkReceiver
        networkReceiver = new NetworkReceiver(this);
    }

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
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkReceiver);
    }

    // טעינת תפריט האפשרויות (Settings, Credits, Logout)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // טיפול בלחיצות על פריטי התפריט
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_settings) {
            Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_credits) {
            Toast.makeText(this, "Credits coming soon", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        refAuth.signOut();

        // איפוס אפשרות ה-"stayConnect" מבלי למחוק את האימייל והסיסמה השמורים
        SharedPreferences settings = getSharedPreferences("RemeberMe", MODE_PRIVATE);
        settings.edit().putBoolean("stayConnect", false).apply();

        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
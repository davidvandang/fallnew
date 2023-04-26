package com.example.fallapplication3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    JournalFragment journalFragment = new JournalFragment();
    SettingFragment settingFragment = new SettingFragment();
    private static final int PERMISSION_REQUEST_CODE = 100;

    // Method to request necessary permissions if not already granted
    private void requestPermissionsIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_REQUEST_CODE);
        } else {
            startFallDetectionService();
        }
    }

    // Method to handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startFallDetectionService();
            } else {
                Toast.makeText(this, "Permissions denied. The app might not work as expected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startFallDetectionService() {
        Intent fallDetectionServiceIntent = new Intent(this, FallDetectionService.class);
        ContextCompat.startForegroundService(this, fallDetectionServiceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        // Set the home fragment as the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        // Set up the bottom navigation item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                        return true;

                    case R.id.miJournal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, journalFragment).commit();
                        return true;

                    case R.id.miSetting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingFragment).commit();
                        return true;

                }
                return false;
            }
        });

        // Request permissions and start the fall detection service
        requestPermissionsIfNeeded();
    }
}

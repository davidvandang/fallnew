package com.example.fallapplication3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private fall fallDetector;

    private boolean isOnline = true;


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    JournalFragment journalFragment = new JournalFragment();
    SettingFragment settingFragment = new SettingFragment();

    @Override
    //Navigation Bar
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,homeFragment).commit();

        // Initialize sensor manager and accelerometer sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Instantiate the fall class
        fallDetector = new fall();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,homeFragment).commit();
                        return true;

                    case R.id.miJournal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,journalFragment).commit();
                        return true;

                    case R.id.miSetting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,settingFragment).commit();
                        return true;

                }
                return false;
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register the accelerometer sensor listener
        sensorManager.registerListener(fallDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the accelerometer sensor listener to save battery
        sensorManager.unregisterListener(fallDetector);
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void startFallDetection() {
        if (isOnline) {
            fallDetector.start();
        }
    }

    public void stopFallDetection() {
        fallDetector.stop();
    }

}
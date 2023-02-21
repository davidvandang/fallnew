package com.example.fallapplication3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    JournalFragment journalFragment = new JournalFragment();
    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,homeFragment).commit();

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

}
package com.example.bottom_menu.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottom_menu.MyApplication;
import com.example.bottom_menu.R;
import com.example.bottom_menu.create.CreateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    BottomNavigationView bottomNavigationView;
    SettingFragment settingFragment = new SettingFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    CreateFragment createFragment = new CreateFragment();
    ScanFragment scanFragment = new ScanFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, scanFragment).commit(); //set the begin fragment to scan
        bottomNavigationView.setSelectedItemId(R.id.Scan); //set the begin tab to scan

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.History:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
                    return true;
                case R.id.Scan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, scanFragment).commit();
                    return true;
                case R.id.Favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favoriteFragment).commit();
                    return true;
                case R.id.Create:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, createFragment).commit();
                    return true;
                case R.id.Settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                    return true;
            }
            return false;
        });

    }
    public static Context getAppContext() {
        return context;
    }
}
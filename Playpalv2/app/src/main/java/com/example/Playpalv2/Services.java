package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.Playpalv2.databinding.ActivityServicesBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Services extends DrawerBase {

    ActivityServicesBinding activityServicesBinding;


    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityServicesBinding = ActivityServicesBinding.inflate(getLayoutInflater());

        setContentView(activityServicesBinding.getRoot());

        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.services);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.messages) {
                startActivity(new Intent(getApplicationContext(), Messages.class));
                overridePendingTransition(0, 0);
                return true;
            } else return itemId == R.id.services;
        });


    }
}
package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DogProfile extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //bottomNavigationView.setSelectedItemId(R.id.services);
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
            } else
                startActivity(new Intent(getApplicationContext(), Services.class));
                overridePendingTransition(0, 0);
                return true;

        });



    }





}
package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView newRecyclerView = findViewById(R.id.newRecyclerView);
        List<NewMatches> newMatches = new ArrayList<>();

        for(int i = 0; i < 15; i++){
            newMatches.add(
                    new NewMatches(
                            "Rocky ".concat(String.valueOf(i)),
                            getResources().getString(R.string.dummy_text)
                    )
            );
        }

        newRecyclerView.setAdapter(new NewMatchesAdapter(newMatches));


        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.messages:
                        return true;
                    case R.id.services:
                        startActivity(new Intent(getApplicationContext(),Services.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
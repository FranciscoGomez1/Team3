package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.Playpalv2.databinding.ActivityMessagesBinding;
import com.example.Playpalv2.databinding.ActivityServicesBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Messages extends DrawerBase {
    ActivityMessagesBinding activityMessagesBinding; //For services


    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMessagesBinding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(activityMessagesBinding.getRoot());

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

        //RecyclerView for NewChat
        RecyclerView newMatchesRecyclerView = findViewById(R.id.newMatchesRecyclerView);
        List<NewChat> newChats = new ArrayList<>();

        //ImageView myImageView = (ImageView) findViewById(R.id.image);

        for(int i = 0; i < 15; i++){
            newChats.add(
                    new NewChat(
                            "Benny ".concat(String.valueOf(i))
                            //getResources().getString(R.string.dummy_text)
                    )
            );
        }

        newMatchesRecyclerView.setAdapter(new NewChatAdapter(newChats));


        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.messages) {
                return true;
            } else if (itemId == R.id.services) {
                startActivity(new Intent(getApplicationContext(), Services.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}
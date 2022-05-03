package com.example.Playpalv2.matches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Playpalv2.DrawerBase;
import com.example.Playpalv2.R;
import com.example.Playpalv2.Services;
import com.example.Playpalv2.databinding.ActivityMessagesBinding;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.matches.GetMatchesList;
import com.example.Playpalv2.models.DogOwnerModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;


public class Messages extends DrawerBase {

    private ActivityMessagesBinding binding;
    private PreferenceManager preferenceManager;
    private GetMatchesList getMatchesList = new GetMatchesList();
    private MatchesAdapter matchesAdapter;

    private List<DogOwnerModel> testDogOwners = new LinkedList<>();
    private DogOwnerModel testOwner = new DogOwnerModel();

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //preferenceManager = new PreferenceManager(getApplicationContext());
        /*
        testOwner.setFirst_name("TestDogOwner");
        testDogOwners.add(testOwner);
        matchesAdapter = new MatchesAdapter(testDogOwners);
        binding.matchesRecyclerView.setAdapter(matchesAdapter);
        binding.matchesRecyclerView.setVisibility(View.VISIBLE);
*/
        loading(true);
        getMatchesList.getMyMatches(matches -> {
            //Log.e("MATCHES", matches.get(0).getFirst_name());
            //Log.e("MATCHES", matches.get(1).getFirst_name());
            matchesAdapter = new MatchesAdapter(this, matches);
           // matchesAdapter = new MatchesAdapter(testDogOwners);
            binding.matchesRecyclerView.setAdapter(matchesAdapter);
            binding.matchesRecyclerView.setVisibility(View.VISIBLE);
            loading(false);
        });
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

    private void getMatches(){
        loading(true);
        getMatchesList.getMyMatches(matches -> {
            Log.e("MATCHES", matches.get(0).getFirst_name());
            Log.e("MATCHES", matches.get(1).getFirst_name());
            loading(false);
            //matchesAdapter = new MatchesAdapter(matches);
            binding.matchesRecyclerView.setAdapter(matchesAdapter);
            binding.matchesRecyclerView.setVisibility(View.VISIBLE);
        });
    }


    private void loading(Boolean isLoading){
        if(isLoading){
            binding.matchesProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            binding.matchesProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
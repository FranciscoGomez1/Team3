package com.example.Playpalv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;

import androidx.fragment.app.FragmentManager;

import com.example.Playpalv2.databinding.ActivityServicesBinding;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.get_from_firestore.GetServiceProviders;
import com.example.Playpalv2.matches.Messages;
import com.example.Playpalv2.models.DogOwnerModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class Services extends DrawerBase {

    ActivityServicesBinding activityServicesBinding;
    FragmentManager manager;
    FrameLayout myframeLayout;
    private  String cont1 = "containerService";

    private  String cont = cont1;
    boolean showingWalking;
    private Switch aSwitch;

    private List<DogOwnerModel> walkers = new LinkedList<>();

    private GetServiceProviders getWalkers = new GetServiceProviders();

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityServicesBinding = ActivityServicesBinding.inflate(getLayoutInflater());
        setContentView(activityServicesBinding.getRoot());
        //set title to top bar
        allocateActivityTitle("Services");
        View frameLayoutView = findViewById(R.id.containerService);
        aSwitch = findViewById(R.id.switch1);
        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.services);

        aSwitch.setOnClickListener(View -> flipCard()); //

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

        //Initialise the main cointainers with the card front fragments
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerService, new servicesWalking())
                    .commit();

        }

       /* getWalkers.fetchWalkers(walkers ->{

            Log.e("walkers", walkers.size() + "");

        });
*/
    }
    //This functions flips the card with a turning animation
    void flipCard() {
        int id = getResources().getIdentifier(cont, "id", getPackageName()); //--> THIS GOT TO CHANGE
        if (showingWalking) {
            showingWalking = false;
            getSupportFragmentManager().popBackStack();
        } else {
            showingWalking = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, new ServicesSitter())
                    .addToBackStack(null)
                    .commit();
        }
    }
    public static class FragmentUtils {
        public static boolean mDisableFragmentAnimations = false;
    }
    // This function turns the card without flip animation
    public void clearBackStack() {
        MainActivity.FragmentUtils.mDisableFragmentAnimations = true;
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        MainActivity.FragmentUtils.mDisableFragmentAnimations = false;
    }
}
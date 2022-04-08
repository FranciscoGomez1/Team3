package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.Playpalv2.databinding.ActivityServicesBinding;
import com.example.Playpalv2.flipCards.CardBackFragment;
import com.example.Playpalv2.flipCards.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DogProfile extends AppCompatActivity {

    ActivityServicesBinding activityServicesBinding;
    FragmentManager manager;
    FrameLayout myframeLayout;
    private  String cont1 = "container_dog_profile_edit";

    private  String cont = cont1;
    boolean showingWalking;
    private Button editProfile;

    private BottomNavigationView bottomNavigationView; //FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        editProfile = findViewById(R.id.editProfile);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.title_activity_dog_profile);

        editProfile.setOnClickListener(View -> flipCard()); //
        //Initialise the main cointainers with the card front fragments
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_dog_profile_edit, new DisplayDogProfile())
                    .commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    void flipCard() {
        int id = getResources().getIdentifier(cont, "id", getPackageName()); //--> THIS GOT TO CHANGE
        if (showingWalking) {
            showingWalking = false;
            getSupportFragmentManager().popBackStack();
        } else {
            showingWalking = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, new EditDogProfile())
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

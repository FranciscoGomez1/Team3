
package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.Playpalv2.databinding.ActivityMainBinding;

import com.example.Playpalv2.flipCards.CardBackFragment;
import com.example.Playpalv2.flipCards.CardFrontFragment;
import com.example.Playpalv2.flipCards.CardFrontFragment1;
import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.flipCards.DogViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends DrawerBase implements View.OnTouchListener {
    //For dragging card
    boolean showingBack = false;
    private float dx;
    private float dy;
    private float ogX;
    private float ogY;
    private int lastAction;
    private Boolean right = false;
    private Boolean left = false;
    private  String cont1 = "container";
    private  String cont2 = "container1";
    private  String cont = cont1;
    private int position;

    DogViewModel dogViewModel;

    ActivityMainBinding activityMainBinding; //This is for the top navigation bar


    private DogModel dog;
    private DogModel dog1;

    FragmentManager manager;

    // FOR TOOLBAR NAVIGATION
    MaterialToolbar toolbar;
    // -->DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout myframeLayout;

    Queue<DogModel> qDogs =new LinkedList<>();

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        qDogs.add(new DogModel("Dog1", "Dog1 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog2", "Dog2 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog3", "Dog3 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog4", "Dog4 bio" + getString(R.string.dummy_dog_bio)));


        dog = qDogs.poll();
        dog1 = qDogs.poll();

        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.init();


        int id = getResources().getIdentifier(cont, "id", getPackageName());
        int id2 = getResources().getIdentifier(cont2, "id", getPackageName());

        View frameLayoutView = findViewById(id);
        View frameLayoutView2 = findViewById(id2);

        // Initialize the queue of dog cards
        intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1);

        //FOR BOTTOM NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.messages) {
                startActivity(new Intent(getApplicationContext(), Messages.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.services) {
                startActivity(new Intent(getApplicationContext(), Services.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        frameLayoutView.setOnTouchListener(this);
        frameLayoutView2.setOnTouchListener(this);

        //Initialise the main cointainers with the card front fragments
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container1, new CardFrontFragment1())
                    .commit();
        }

        ogX = frameLayoutView.getX();
        ogY = frameLayoutView.getY();
    }

    private void intDogViewModel(View frameLayoutView, View frameLayoutView2, DogModel dog, DogModel dog1) {

        dogViewModel.updateDog(dog);
        dogViewModel.updateDog1(dog1);

        if( dog == null) {
            frameLayoutView.setVisibility(View.INVISIBLE);
            frameLayoutView2.setVisibility(View.INVISIBLE);
        }else if(dog1 == null){
            frameLayoutView2.setVisibility(View.INVISIBLE);
        }
        else{
            dogViewModel.updateDog1(dog1);
        }
    }

    @Override // This function registers the input of the user on the screen
    public boolean onTouch(View view, MotionEvent motionEvent) {
        View v1 = findViewById(R.id.container);
        View v2 = findViewById(R.id.container1);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastAction = MotionEvent.ACTION_DOWN;
                dx = view.getX() - motionEvent.getRawX();
                dy = view.getY() - motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE: // If the user drags the card
                lastAction = MotionEvent.ACTION_MOVE;
                view.setX(motionEvent.getRawX() + dx);
                view.setY(motionEvent.getRawY() + dy);
                if(view.getX() > 520){
                    right = true;
                    Log.i("Should Be deleted:", String.valueOf(right));
                }else if(view.getX() < -520){
                    left = true;
                }

                Log.i("VIEW X:", String.valueOf(view.getX()));
                //Toast.makeText(this,"Works", Toast.LENGTH_SHORT ).show();
                break;
            case MotionEvent.ACTION_UP:
                //view.setVisibility(View.INVISIBLE);

                if (right) {
                    //Reset stack
                    Log.i("Should Be deleted:", String.valueOf(view));
                    right = false;
                    dog = qDogs.poll();
                    resetCards(v1, v2, view, dog);
                }else if(left){

                    Log.i("Should Be deleted:", String.valueOf(view));
                    left = false;
                    dog = qDogs.poll();
                    resetCards(v1, v2, view, dog);
                }
                view.setX(ogX);
                view.setY(ogY);
                //view.setVisibility(View.VISIBLE);
                if(lastAction == MotionEvent.ACTION_DOWN){
                    flipCard();
                }
                break;
            default:
                break;

        }

        return true;
    }

    private void resetCards(View v1, View v2, View view, DogModel dog) {

        if(cont.equals(cont1)){

            v2.setElevation(0);
            v1.setElevation(-1);
            cont = cont2;
            if(dog != null){
                dogViewModel.updateDog(dog);
            }else {
                v1.setVisibility(View.INVISIBLE);
            }

        }else{
            cont = cont1;
            v2.setElevation(-1);
            v1.setElevation(0);
            if(dog != null) {
                dogViewModel.updateDog1(dog);
            }else {
                v2.setVisibility(View.INVISIBLE);
            }
        }
        /*
        Log.i("View elevation:", String.valueOf(view.getElevation()));
        Log.i("Should Be deleted:", String.valueOf(view));
        Log.i("Should Be deleted:", "RIGHT SWIPE");
        */


        if(showingBack){
            showingBack = false;
            clearBackStack();
        }
    }

    //This functions flips the card with a turning animation
    void flipCard() {
        int id = getResources().getIdentifier(cont, "id", getPackageName());
        if (showingBack) {
            showingBack = false;
            getSupportFragmentManager().popBackStack();
        } else {
            showingBack = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.card_flip_right_in,
                            R.anim.card_flip_right_out,
                            R.anim.card_flip_left_in,
                            R.anim.card_flip_left_out)
                    .replace(id, new CardBackFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
    public static class FragmentUtils {
        public static boolean mDisableFragmentAnimations = false;
    }

    // This function turns the card without flip animation
    public void clearBackStack() {
        FragmentUtils.mDisableFragmentAnimations = true;
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentUtils.mDisableFragmentAnimations = false;
    }
}



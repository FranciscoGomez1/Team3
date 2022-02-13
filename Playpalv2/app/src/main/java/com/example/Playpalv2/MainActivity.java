package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    //For dragging card
    boolean showingBack = false;
    private float dx;
    private float dy;
    private int lastAction;
    private Boolean right = false;
    private Boolean left = false;
    private  String cont1 = "container";
    private  String cont2 = "container2";
    private  String cont = cont1;

    // FOR TOOLBAR NAVIGATION
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout myframeLayout;

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FOR TOP NAVIGATION BAR


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(false);

        myframeLayout = findViewById(R.id.main_frameLayout);
        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        int id = getResources().getIdentifier(cont, "id", getPackageName());
        int id2 = getResources().getIdentifier(cont2, "id", getPackageName());

        View frameLayoutView = findViewById(id);
        View frameLayoutView2 = findViewById(id2);


        //FOR BOTTOM NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(),Messages.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.services:
                        startActivity(new Intent(getApplicationContext(),Services.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        frameLayoutView.setOnTouchListener(this);
        frameLayoutView2.setOnTouchListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container2, new CardFrontFragment())
                    .commit();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        View v1 = findViewById(R.id.container);
        View v2 = findViewById(R.id.container);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastAction = MotionEvent.ACTION_DOWN;
                dx = view.getX() - motionEvent.getRawX();
                dy = view.getY() - motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastAction = MotionEvent.ACTION_MOVE;
                view.setX(motionEvent.getRawX() + dx);
                view.setY(motionEvent.getRawY() + dy);
                if(view.getX() > 520){
                    right = true;
                    Log.i("Should Be deleted:", String.valueOf(right));
                    //Toast.makeText(this,"Works", Toast.LENGTH_SHORT ).show();
                }else if(view.getX() < -520){
                    left = true;
                }

                Log.i("VIEW X:", String.valueOf(view.getX()));
               //Toast.makeText(this,"Works", Toast.LENGTH_SHORT ).show();
                break;
            case MotionEvent.ACTION_UP:
                view.setVisibility(View.INVISIBLE);


                try {
                    if (right == true) {
                        Log.i("Should Be deleted:", String.valueOf(view));
                        right = false;
                        if(cont == cont1){

                            v2.setElevation(0);
                            v1.setElevation(-1);
                            cont = cont2;
                        }else{
                            cont = cont1;
                            v2.setElevation(-1);
                            v1.setElevation(0);
                        }
                        Log.i("View elevation:", String.valueOf(view.getElevation()));
                        //view.setVisibility(View.GONE); // this is to show it
                        Log.i("Should Be deleted:", String.valueOf(view));
                        Log.i("Should Be deleted:", "RIGHT SWIPE");

                        if(showingBack){
                            showingBack = false;
                            clearBackStack();
                        }
                    }else if(left == true){

                        Log.i("Should Be deleted:", String.valueOf(view));
                        left = false;
                        if(cont == cont1){

                            v2.setElevation(0);
                            v1.setElevation(-1);
                            cont = cont2;
                        }else{
                            cont = cont1;
                            v2.setElevation(-1);
                            v1.setElevation(0);
                        }
                        Log.i("View elevation:", String.valueOf(view.getElevation()));
                        //view.setVisibility(View.GONE); // this is to show it
                        Log.i("Should Be deleted:", String.valueOf(view));
                        Log.i("Should Be deleted:", "RIGHT SWIPE");

                        if(showingBack){
                            showingBack = false;
                            clearBackStack();
                        }

                    }
                }catch (Exception e){
                    Log.i("Should Be deleted:", "Something Whent wrong");
                }
                view.setX(0);
                view.setY(0);
                view.setVisibility(View.VISIBLE);
                if(lastAction == MotionEvent.ACTION_DOWN){
                   flipCard();
                }
                break;
        }

        return true;
    }
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
    public static class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
        @Override
        public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
            if (FragmentUtils.mDisableFragmentAnimations) {
                Animation a = new Animation() {};
                a.setDuration(0);
                return a;
            }
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
        @Override
        public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
            if (FragmentUtils.mDisableFragmentAnimations) {
                Animation a = new Animation() {};
                a.setDuration(0);
                return a;
            }
            return super.onCreateAnimation(transit, enter, nextAnim);
        }

    }
    public void clearBackStack() {
        FragmentUtils.mDisableFragmentAnimations = true;
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentUtils.mDisableFragmentAnimations = false;
    }
}


package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends FragmentActivity implements View.OnTouchListener {
    //For dragging card
    boolean showingBack = false;
    private float dx;
    private float dy;
    private int lastAction;
    private Boolean right;
    private  String cont1 = "container";
    private  String cont2 = "container2";
    private  String cont = cont1;

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FOR NAVIGATION BAR
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
        int id = getResources().getIdentifier(cont, "id", getPackageName());
        int id2 = getResources().getIdentifier(cont2, "id", getPackageName());

        View frameLayoutView = findViewById(id);
        View frameLayoutView2 = findViewById(id2);

        frameLayoutView.setOnTouchListener(this);
        frameLayoutView2.setOnTouchListener(this);

        FrameLayout frameLayout = findViewById(R.id.container);
        FrameLayout frameLayout2 = findViewById(R.id.container2);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }



        });


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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
                }
                Log.i("VIEW X:", String.valueOf(view.getX()));
               //Toast.makeText(this,"Works", Toast.LENGTH_SHORT ).show();
                break;
            case MotionEvent.ACTION_UP:
                try {
                    if (right == true) {

                        Log.i("Should Be deleted:", String.valueOf(view));
                        right = false;
                        if(cont == cont1){
                            cont = cont2;
                        }else{cont = cont1;}
                        view.setVisibility(View.GONE); // this is to show it
                        Log.i("Should Be deleted:", String.valueOf(view));
                        Log.i("Should Be deleted:", "RIGHT SWIPE");
                    }
                }catch (Exception e){
                    Log.i("Should Be deleted:", "Something Whent wrong");
                }
                view.setX(0);
                view.setY(0);
                if(lastAction == MotionEvent.ACTION_DOWN){
                    flipCard();
                }
                break;
        }
        return true;
    }

    public static class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
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
    }

}


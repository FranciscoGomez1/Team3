
package com.example.Playpalv2.flipCards;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.Playpalv2.DrawerBase;
import com.example.Playpalv2.Messages;
import com.example.Playpalv2.CardProfileFragment;
import com.example.Playpalv2.R;
import com.example.Playpalv2.Services;
import com.example.Playpalv2.databinding.ActivityMainBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.button.MaterialButton;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class MainActivity extends DrawerBase implements View.OnTouchListener {
    //For dragging card
    private boolean showingBack = false;
    private boolean isProfile = false;
    private boolean canFlip = true;
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
    private String replaceTextForShoDogProfileBtn = "Back To Dogs";
    private String setOriginalTextForShoDogProfileBtn = "View Profile";

    private int position;
    private Integer doubleTapFlag = 0;

    private MaterialButton likeBtn;
    private MaterialButton dislikeBtn;
    private MaterialButton showDogProfile;


    DogViewModel dogViewModel;

    ActivityMainBinding activityMainBinding; //This is for the top navigation bar


    private DogModel dog;
    private DogModel dog1;
    private TextView profileBtn;
    FragmentManager manager;

    // FOR TOOLBAR NAVIGATION
    MaterialToolbar toolbar;
    // -->DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout myframeLayout;

   Queue<DogModel> qDogs = new LinkedList<>();

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    FirebaseFirestore db;
    //FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        FirebaseFirestore db;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        //set title to top bar
        allocateActivityTitle("Home");
        //new waitForFirebase().execute();

/*
        qDogs.add(new DogModel("Dog1", "Dog1 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog2", "Dog2 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog3", "Dog3 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog4", "Dog4 bio" + getString(R.string.dummy_dog_bio)));*/
       // qDogs.add(new DogModel("Dog3", "Dog3 bio" + getString(R.string.dummy_dog_bio)));


       /* dog = qDogs.poll();
        dog1 = qDogs.poll();
*/
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.init();


        int id = getResources().getIdentifier(cont, "id", getPackageName());
        int id2 = getResources().getIdentifier(cont2, "id", getPackageName());

        View frameLayoutView = findViewById(id);
        View frameLayoutView2 = findViewById(id2);

        getDogs(frameLayoutView, frameLayoutView2);
       /* // Initialize the queue of dog cards
        intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1);*/

        likeBtn = findViewById(R.id.btn_like);

        dislikeBtn = findViewById(R.id.btn_dislike);
        showDogProfile = findViewById(R.id.btn_show_profile);

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
 
        Log.i("FRAMELAOUT CONTEXT", frameLayoutView.getContext().toString());
        ogX = frameLayoutView.getX();
        ogY = frameLayoutView.getY();

        likeBtn.setOnClickListener(view -> {
            dog = qDogs.poll();
            viewProfile();
            resetBtnText();
            canFlip = !canFlip;


            resetCards(frameLayoutView, frameLayoutView2, view, dog);
        });

        dislikeBtn.setOnClickListener(view -> {
            dog = qDogs.poll();
            viewProfile();
            resetBtnText();

            canFlip = !canFlip;
            resetCards(frameLayoutView, frameLayoutView2, view, dog);

        });

        showDogProfile.setOnClickListener(view -> {
            showDogProfile.setText(replaceTextForShoDogProfileBtn);
            viewProfile();
            resetBtnText();
            canFlip = !canFlip;
        });


    }


    void resetBtnText(){
        if(!canFlip){
            showDogProfile.setText(setOriginalTextForShoDogProfileBtn);
        }
    }

    private void getDogs(View frameLayoutView, View frameLayoutView2){
        //FirebaseFirestore db;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String[] doc = new String[1];
        final CollectionReference[] thisCollecRef = new CollectionReference[1];
        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database
        CollectionReference collecRef = db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs");

        collecRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.i("TAG","ONSUCESS: IT WORKS");
                List<DocumentSnapshot> snapshotList = Objects.requireNonNull(task.getResult()).getDocuments();

                try {
                    for (DocumentSnapshot snapshot : snapshotList) {
                        Log.i("TAG", Objects.requireNonNull(snapshot.getId()));
                        qDogs.add(new DogModel(Objects.requireNonNull(snapshot.get("Age")).toString(),
                                Objects.requireNonNull(snapshot.get("Bio")).toString(),
                                Objects.requireNonNull(snapshot.get("Breed")).toString(),
                                (List<String>) snapshot.get("Images"),
                                Objects.requireNonNull(snapshot.get("Name")).toString(),
                                Objects.requireNonNull(snapshot.get("Owner")).toString(),
                                Objects.requireNonNull(snapshot.get("Sex")).toString(),
                                Objects.requireNonNull(snapshot.get("Weight")).toString()));
                    }
                }catch(Exception e){
                    Log.i("EXEPTION", e.getMessage());
                }
                dog = qDogs.poll();
                dog1 = qDogs.poll();

                // Initialize the queue of dog cards
                intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1);

            }
        });

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
        if(canFlip){
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doubleTapFlag += 1;
                    // lastAction = MotionEvent.ACTION_DOWN;
                    dx = view.getX() - motionEvent.getRawX();
                    dy = view.getY() - motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE: // If the user drags the card
                    doubleTapFlag = 0;
                    lastAction = MotionEvent.ACTION_MOVE;
                    view.setX(motionEvent.getRawX() + dx);
                    view.setY(motionEvent.getRawY() + dy);
                    if (view.getX() > 520) {
                        right = true;
                        Log.i("Should Be deleted:", String.valueOf(right));
                    } else if (view.getX() < -520) {
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
                    } else if (left) {

                        Log.i("Should Be deleted:", String.valueOf(view));
                        left = false;
                        dog = qDogs.poll();
                        resetCards(v1, v2, view, dog);
                    }
                    view.setX(ogX);
                    view.setY(ogY);
                    //view.setVisibility(View.VISIBLE);
                    if (doubleTapFlag == 2 && canFlip) {
                        //if(lastAction == MotionEvent.ACTION_DOWN){
                        doubleTapFlag = 0;
                        flipCard();

                    }
                    break;
                default:
                    break;
            }

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
    // This function adds view profile fragment
    void viewProfile() {
        int id = getResources().getIdentifier(cont, "id", getPackageName());
        if (isProfile) {
            isProfile = false;
            /*canFlip = true;*/
            getSupportFragmentManager().popBackStack();
        } else {
            isProfile = true;
          /*  canFlip = false;*/
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, new CardProfileFragment())
                    .addToBackStack(null)
                    .commit();
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



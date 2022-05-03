
package com.example.Playpalv2.flipCards;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Playpalv2.DrawerBase;
import com.example.Playpalv2.MessagesAgi;
import com.example.Playpalv2.CardProfileFragment;
import com.example.Playpalv2.R;
import com.example.Playpalv2.Services;

import com.example.Playpalv2.databinding.ActivityMainBinding;
import com.example.Playpalv2.firestore_updates.RecordUserChoice;
import com.example.Playpalv2.get_from_firestore.GetDogOwner;
import com.example.Playpalv2.get_from_firestore.GetDogs;
import com.example.Playpalv2.matches.GetMatchesList;
import com.example.Playpalv2.matches.Messages;
import com.example.Playpalv2.models.CardModel;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.models.CardsModel;
import com.example.Playpalv2.view_models.CardsQueueViewModel;
import com.example.Playpalv2.view_models.DogOwnerView;
import com.example.Playpalv2.view_models.DogQueueViewModel;
import com.example.Playpalv2.view_models.DogViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.google.android.material.button.MaterialButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
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

    private DogOwnerModel thisDogOwner;

    private FloatingActionButton likeBtn;
    private FloatingActionButton dislikeBtn;
    private MaterialButton showDogProfile;


    private DogViewModel dogViewModel;
    private DogOwnerView dogOwnerView;
    public DogQueueViewModel dogQueueViewModel;
    private ActivityMainBinding activityMainBinding; //This is for the top navigation bar



    private LinearLayout buttons;
    private FrameLayout f;
    private FrameLayout f1;

    private DogModel dogCard;
    private DogModel dogCard1;
    private DogModel topDog;
    private CardModel card;
    private CardModel card1;

    private TextView profileBtn;
    private FragmentManager manager;

    // FOR TOOLBAR NAVIGATION
    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout myframeLayout;

    private CardsQueueViewModel cardsQueueViewModel;
    private Queue<CardModel> qCards = new LinkedList<>();
    private Queue<DogModel> qDogs = new LinkedList<>();
    private GetDogs getDogoos = new GetDogs();
    private RecordUserChoice recordUserChoice;
    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR
    private CardsModel cardsModel;
    FirebaseFirestore db;

    private GetMatchesList getMatchesList = new GetMatchesList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        FirebaseFirestore db;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        recordUserChoice = new RecordUserChoice();

        loading(true);

    //set title to top bar
        allocateActivityTitle("Home");
        //new waitForFirebase().execute();

/*
        qDogs.add(new DogModel("Dog1", "Dog1 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog2", "Dog2 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog3", "Dog3 bio" + getString(R.string.dummy_dog_bio)));
        qDogs.add(new DogModel("Dog4", "Dog4 bio" + getString(R.string.dummy_dog_bio)));*/
       // qDogs.add(new DogModel("Dog3", "Dog3 bio" + getString(R.string.dummy_dog_bio)));


        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.init();
        dogOwnerView = new ViewModelProvider(this).get(DogOwnerView.class);
        dogOwnerView.init();
       /* dog = qDogs.poll();
        dog1 = qDogs.poll();
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.init();*/
       /* cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
        cardViewModel.init();*/

        int id = getResources().getIdentifier(cont, "id", getPackageName());
        int id2 = getResources().getIdentifier(cont2, "id", getPackageName());
        View v1 = findViewById(R.id.container);
        View v2 = findViewById(R.id.container1);

        View frameLayoutView = findViewById(id);
        View frameLayoutView2 = findViewById(id2);

       getDogs(frameLayoutView, frameLayoutView2);
       /* // Initialize the queue of dog cards
        intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1);*/

        likeBtn = findViewById(R.id.btn_like);

        dislikeBtn = findViewById(R.id.btn_dislike);
        showDogProfile = findViewById(R.id.btn_show_profile);
        buttons = findViewById(R.id.buttons);
        f = findViewById(R.id.container);
        f1 = findViewById(R.id.container1);

        //FOR BOTTOM NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.messages) {
                loading(false);
                startActivity(new Intent(getApplicationContext(), Messages.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.services) {
                loading(false);
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
            viewProfile();
            resetBtnText();
            canFlip = !canFlip;
            dogGotLiked(frameLayoutView, frameLayoutView2, view);
        });

        dislikeBtn.setOnClickListener(view -> {
            viewProfile();
            resetBtnText();
            canFlip = !canFlip;
            dogGotDisLiked(frameLayoutView, frameLayoutView2, view);
          //  resetCards(frameLayoutView, frameLayoutView2, view, dog);

        });

        showDogProfile.setOnClickListener(view -> {
            showDogProfile.setText(replaceTextForShoDogProfileBtn);
            viewProfile();
            resetBtnText();
        });

    }

    void resetBtnText(){
        if(!canFlip){
            showDogProfile.setText(setOriginalTextForShoDogProfileBtn);
        }
    }
//-->

    private void getDogs(View frameLayoutView, View frameLayoutView2){
        GetDogs getDogs = new GetDogs();
        getDogs.fetchDogs(dogs -> {

            if (dogs.size() > 1) {
                f.setVisibility(View.VISIBLE);
                f1.setVisibility(View.VISIBLE);
                buttons.setVisibility(View.VISIBLE);
            }else if(dogs.size() == 1){
                f.setVisibility(View.VISIBLE);
                buttons.setVisibility(View.VISIBLE);
            }
            cardsModel = new CardsModel(dogs);
            /*qDogs = dogs;
            dog = qDogs.poll();
            topDog = dog;
            dog1 = qDogs.poll();*/
        //    getDogOwner(dog);
            //getDogOwner(dog1);
            loading(false);
            intDogViewModel(frameLayoutView, frameLayoutView2 , cardsModel.getTopDogCard(), cardsModel.getBottomDogCard()); // This has to become a cardclass that holds a dog owner and a dog.
        });
        //FirebaseFirestore db;
       /* FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String[] doc = new String[1];
        final CollectionReference[] thisCollecRef = new CollectionReference[1];*/

       /* db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database
        CollectionReference collecRef = db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs");

        collecRef.whereLessThan("Age",9).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        //collecRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1); // This has to become a cardclas that holds a dog owner and a dog.

            }
        });*/
      /*  BuildCard cards = new BuildCard();
        cards.getCards(new OnGotCardsListener() {
            @Override
            public void onGotCards(Queue<CardModel> cards) {
                Log.e("Card", cards.toString());

                qCards = cards;
                card = qCards.poll();
                card1 = qCards.poll();
                intCardViewModel(frameLayoutView, frameLayoutView2 , card, card1); // This has to become a cardclass that holds a dog owner and a dog.

            }
        });*/

        /*GetDogs dogs = new GetDogs();
        dogs.fetchDogs(new OnGotDogsListener(){
         @Override
            public void onGotDogs(Queue<DogModel> dogs) {
                qDogs = dogs;
                Log.e("GETDOGS", qDogs.toString());
                dog = qDogs.poll();
                dog1 = qDogs.poll();
             intDogViewModel(frameLayoutView, frameLayoutView2 , dog, dog1); // This has to become a cardclass that holds a dog owner and a dog.

         }
        });*/
/*
        getMatchesList.getMyMatches( matches -> {
            Log.e("MATCHES", matches.get(0).getFirst_name());
            Log.e("MATCHES", matches.get(1).getFirst_name());
        });*/

    }


    private void intDogViewModel(View frameLayoutView, View frameLayoutView2, DogModel dogCard, DogModel dogCard1) {
        dogViewModel.updateDog(dogCard);
        dogViewModel.updateDog1(dogCard1);
        initDogOwners(dogCard, dogCard1);
        if( dogCard == null) {
            frameLayoutView.setVisibility(View.INVISIBLE);
            frameLayoutView2.setVisibility(View.INVISIBLE);
            buttons.setVisibility(View.INVISIBLE);
        }else if(dogCard1 == null){
            frameLayoutView2.setVisibility(View.INVISIBLE);
        }
        else{
            dogViewModel.updateDog1(dogCard1);
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
                    } else if (view.getX() < 520 || view.getX() > -520 ){
                        right = false;
                        left = false;
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
                        dogGotLiked(v1,v2,view);
                        //dog = qDogs.poll();
                        //resetCards(v1, v2, view, dog);
//                        resetCards(v1, v2, view, dog);
                    } else if (left) {

                        Log.i("Should Be deleted:", String.valueOf(view));
                        left = false;
                        dogGotDisLiked(v1,v2,view);
                        //dog = qDogs.poll();
                       // resetCards(v1, v2, view, dog);
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

    private void resetCards(View v1, View v2, View view, DogModel dogCard) {

        if(cont.equals(cont1)){

            v2.setElevation(0);
            v1.setElevation(-1);
            cont = cont2;
            if(dogCard != null){
                dogViewModel.updateDog(dogCard);
                getDogOwner(dogCard);

            }else {
                v1.setVisibility(View.INVISIBLE);
            }

        }else{
            cont = cont1;
            v2.setElevation(-1);
            v1.setElevation(0);
            if(dogCard != null) {
                dogViewModel.updateDog(dogCard);
                getDogOwner1(dogCard);
            }else {
                v2.setVisibility(View.INVISIBLE);
                buttons.setVisibility(View.INVISIBLE);
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
        }else if (cont.equals("container")){
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
        }else{
            showingBack = true;
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.card_flip_right_in,
                                R.anim.card_flip_right_out,
                                R.anim.card_flip_left_in,
                                R.anim.card_flip_left_out)
                        .replace(id, new CardBackFragment1())
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

    void dogGotLiked(View frameLayoutView, View frameLayoutView2, View view){
//        recordUserChoice.userLikesThisOwnerDog(dog.getOwner());
        recordUserChoice.userLikesThisOwnerDog(cardsModel.getTopDogCard().getOwner());
        dogCard = cardsModel.pullCard();
      //  getDogOwner(dog);
        resetCards(frameLayoutView, frameLayoutView2, view, dogCard);
    }
    void dogGotDisLiked(View frameLayoutView, View frameLayoutView2, View view){
        recordUserChoice.userDisLikesThisOwnerDog(cardsModel.getTopDogCard().getOwner());
        dogCard = cardsModel.pullCard();
       // getDogOwner(dog);
        resetCards(frameLayoutView, frameLayoutView2, view, dogCard);
    }

    void initDogOwners(DogModel dog, DogModel dog1){
        try {
            GetDogOwner owner = new GetDogOwner(dog.getOwner());
            owner.getOwner(dogOwner -> {
                dogOwnerView.updateOwner(dogOwner);
                Log.e("When did OnGotOwnerGot", dog.getName());
            });

            GetDogOwner owner1 = new GetDogOwner(dog1.getOwner());
            owner1.getOwner(dogOwner -> {
                dogOwnerView.updateOwner1(dogOwner);
                Log.e("When did OnGotOwnerGot1", dog1.getName());
            });
        }catch (Exception e){
            e.getMessage();
        }
    }
    void getDogOwner(DogModel dog ){
        try {
            GetDogOwner owner = new GetDogOwner(dog.getOwner());
            owner.getOwner(dogOwner -> {
                thisDogOwner = dogOwner;
                dogOwnerView.updateOwner(thisDogOwner);
                Log.e("When did OnGotOwnerGot", "now");
            });
        }catch (Exception e){
            e.getMessage();
        }
    }
    void getDogOwner1(DogModel dog ){
        try {
            GetDogOwner owner = new GetDogOwner(dog.getOwner());
            owner.getOwner(dogOwner -> {
                thisDogOwner = dogOwner;
                dogOwnerView.updateOwner1(thisDogOwner);
                Log.e("When did OnGotOwnerGot", "now");
            });
        }catch (Exception e){
            e.getMessage();
        }
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            activityMainBinding.mainProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            activityMainBinding.mainProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}



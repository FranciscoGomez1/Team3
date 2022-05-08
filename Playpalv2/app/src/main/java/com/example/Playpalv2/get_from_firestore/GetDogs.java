package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.UserFilterPreferences;
import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.matches.DogFilter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


public class GetDogs {
    private FirebaseFirestore db;
    private Queue<DogModel> qDogs = new LinkedList<>();
    private MainActivity mainActy;
    private int dogsTobeCheck = 0;
    private int dogsCheck = 0;
    private DogModel dog = new DogModel();
    private String mAuth = FirebaseAuth.getInstance().getUid();
    private GetUserFilterPreferences getUserFilterPreferences;
    private UserFilterPreferences userFilterPreferences = new UserFilterPreferences();
    private DogFilter dogFilter =  new DogFilter();


    public void fetchDogs(OnGotDogsListener onGotDogsListener1){
        getUserFilterPreferences = new GetUserFilterPreferences();
        getUserFilterPreferences.getUserPreferences(userFilterPreferences -> {
            getTheDogs(onGotDogsListener1, userFilterPreferences);
            Log.e("TEST", "isgood");
        });
    }

    private void getTheDogs(OnGotDogsListener onGotDogsListener, UserFilterPreferences userFilterPreferences) {
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database
        CollectionReference collecRef = db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs");


        //Query query = collecRef.whereLessThan("age", 11).whereNotEqualTo("owner", mAuth);

        collecRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    dog = Objects.requireNonNull(documentSnapshot.toObject(DogModel.class));
                    // Here is when i check if a user has been seen by the ower if not seed by the owner add to teh qDogs
                    // to be display in the queue of cards.
                    hasSeenDog(dog, onGotDogsListener, userFilterPreferences);
                    //tasks.add();//db.collection("Dog Owners").document(mAuth).collection("dogsSeen").get());
                    dogsTobeCheck++;
                }
            }
        });

    }

    private void hasSeenDog(DogModel dog, OnGotDogsListener onGotDogsListener, UserFilterPreferences userFilterPreferences) {

        Log.e("DID DOG GOT?", "YES");
        DocumentReference docRef = db.collection("Dog Owners").document(mAuth).
                collection("dogsSeen").document(dog.getOwner());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){

                   DocumentSnapshot document = task.getResult();
                   if(document.exists()) {
                       addDogsChecked();
                       Log.e("Dog has Seen", dog.getName());
                      // onGotDogsListener.onGotDogs(qDogs);
                       Log.e("DogsTobeCahecked", String.valueOf(dogsCheck));
                   }else{
                       addDogsChecked();
                       dogFilter = new DogFilter(dog, userFilterPreferences);
                      if(dogFilter.doesDogPassFilter()) {
                          qDogs.add(dog);
                      }

                   }
                   Log.e("Dog has not Seen", dog.getName());
                   Log.e("DogsTobeCahecked", String.valueOf(dogsCheck));
                   Log.e("dogsTobeCheck", String.valueOf(dogsTobeCheck));
                   if(dogsTobeCheck  == dogsCheck) {
                       Log.e("DOES IT RUN INSIDE ", "YES?");
                       onGotDogsListener.onGotDogs(qDogs);
                   }
               }
               else{
                   Log.e("get failed with", task.getException().toString());
               }

            }

        });
        Log.e("CHECKED ALL DOGS", qDogs.toString());
    }

    void addDogsChecked(){
        dogsCheck++;
    }
    void addToQ(DogModel dog, OnGotDogsListener onGotDogsListener){
        qDogs.add(dog);
        Log.e("QDOGS", qDogs.toString());
        //IT WORKS BUT IT IS UGLY OH WELL IT IS WHAT IT IS ¯\_(ツ)_/¯
        if(dogsTobeCheck   == dogsCheck) {
            onGotDogsListener.onGotDogs(qDogs);
        }
    }


}

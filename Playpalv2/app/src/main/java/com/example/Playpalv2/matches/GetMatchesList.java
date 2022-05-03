package com.example.Playpalv2.matches;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.get_from_firestore.UsersMatchArray;
import com.example.Playpalv2.models.DogOwnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class GetMatchesList {
    private FirebaseFirestore db;
    private List<DogOwnerModel> matches;
    private String userId = FirebaseAuth.getInstance().getUid();
    private CollectionReference matchesCollRef;
    private String matchCollectionPath = "Dog_Matches";
    private String ownersCollection = "Dog Owners";
    private Query query;
    private Query ownerQuerry;
    private String dogOwnerName;
    private static final String TAG = "MainActivity";
    private UsersMatchArray usersMatchArray = new UsersMatchArray();
    private String matchee;
    private DocumentReference ownerDogDocumentRef;

    private List<String> returnMatches = new LinkedList<>();
    private DogOwnerModel dogOwner = new DogOwnerModel();
    private Integer counter = 0;
    private Integer counter2 = 0;

    public interface MatchesCallBack {
        void gotMatches(List<DogOwnerModel> matches);
    }
    public GetMatchesList() {
        matches = new ArrayList<DogOwnerModel>();
        //userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        matchesCollRef = db.collection(matchCollectionPath);
    }

    public void getMyMatches(MatchesCallBack matchesCallBack){
        getTheMatches(matchesCallBack);
    }

    public void getTheMatches(MatchesCallBack matchesCallBack) {
        query = matchesCollRef.whereArrayContains("users", userId);
      /*  query = matchesCollRef.whereArrayContains("users", userId);
        query.get().addOnSuccessListener(querySnapshot -> {
            final int[] counter = {0};
            for(QueryDocumentSnapshot document : querySnapshot){
                usersMatchArray = Objects.requireNonNull(document.toObject(UsersMatchArray.class));
                matchee = usersMatchArray.getMatchee(userId);
                ownerDogDocumentRef = db.collection(ownersCollection).document(matchee);
                ownerDogDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            matches.add(task.getResult().toObject(DogOwnerModel.class));
                            dogOwner = task.getResult().toObject(DogOwnerModel.class);
                            counter[0] += 1;
                            Log.e("ONWER_NAME:", dogOwner.getFirst_name());
                            if(counter[0] == querySnapshot.size() - 1) {
                                Log.e("MATCHES", matches.get(0).getFirst_name());
                            }
                        }
                    }
                //Log.e("MATCHES", matches.get(0).getFirst_name());
                });
            }
        });*/
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()){
                   for(QueryDocumentSnapshot document : task.getResult()){
                       counter++;
                       usersMatchArray = Objects.requireNonNull(document.toObject(UsersMatchArray.class));
                       matchee = usersMatchArray.getMatchee(userId);
                       ownerDogDocumentRef = db.collection(ownersCollection).document(matchee);
                       ownerDogDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    counter2++;
                                    matches.add(task.getResult().toObject(DogOwnerModel.class));
                                    dogOwner = task.getResult().toObject(DogOwnerModel.class);
                                    if(counter == counter2) {
                                        matchesCallBack.gotMatches(matches);
                                    }
                                }
                            }
                       });
                      // Log.e("ONWER_NAME:", dogOwner.getFirst_name());
                      // Log.e("MATCHES", matches.get(0).getFirst_name());
                   }
               }
            }
        });
    }
}
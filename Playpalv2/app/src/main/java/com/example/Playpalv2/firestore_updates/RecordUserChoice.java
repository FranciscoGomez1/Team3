package com.example.Playpalv2.firestore_updates;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Playpalv2.HashMaps.MatchHasMap;
import com.example.Playpalv2.registration.Register3;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecordUserChoice {
    private String dogOwnersCollection = "Dog Owners";
    private String dogsLikedCollection = "dogsLiked";
    private String dogsDisLikedCollection = "dogsDisLiked";
    private String dogsSeenCollection = "dogsSeen";
    private String dogMatches = "Dog_Matches";
    private String matchId;

    private List<String> users = new LinkedList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userID;
    private String firebaseUser = auth.getCurrentUser().getUid();
    Map<String, Object> dogOwner = new HashMap<>();

    private DocumentReference dogsLiked = db.collection(dogOwnersCollection).
            document(firebaseUser);

    private DocumentReference dogsDisLiked = db.collection(dogOwnersCollection).
            document(firebaseUser);

    private DocumentReference dogsSeen = db.collection(dogOwnersCollection).
            document(firebaseUser);

    private CollectionReference isMatch =  db.collection(dogMatches);

    public RecordUserChoice() {
        userID = firebaseUser;

    }


    public RecordUserChoice(String userID) {
        this.userID = userID;
    }


    private void doesTheLikedDogOwnerLikesMyDog(String ownerId){
        DocumentReference isMydogLikedByThisOwner = db.collection(dogOwnersCollection).document(ownerId).
                collection(dogsLikedCollection).document(userID);


        isMydogLikedByThisOwner.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        Log.e("Is A Match", "YES");
                        // onGotDogsListener.onGotDogs(qDogs);
                        recordTheMatch(userID,ownerId);


                    }else{

                       // Log.e("Dog has not Seen", dog.getName());
                    }
                }
                else{
                    Log.e("get failed with", task.getException().toString());
                }

            }

        });
    }

    private void recordTheMatch(String userID, String ownerId) {
        matchId = setMatchDocumentId(userID, ownerId);


        MatchHasMap matchHasMap = new MatchHasMap();
        matchHasMap.addTopMap(users);
        isMatch.document(matchId).set(matchHasMap.getMap());
    }

    private String setMatchDocumentId(String userId, String ownerId) {
        int comparedResult = userId.compareTo(ownerId);

        if (comparedResult > 0) {
            users.add(userID);
            users.add(ownerId);
            return userId + ownerId;
        } else if (comparedResult < 0) {
            users.add(ownerId);
            users.add(userID);
            return ownerId + userId;
        }
        return null;
    }

    public void userLikesThisOwnerDog(String ownerId){
        createDogObject(ownerId);
        dogsLiked.collection(dogsLikedCollection).document(ownerId).set(dogOwner);
        //Check if this dog owner likes the user dog owner
        doesTheLikedDogOwnerLikesMyDog(ownerId);
        userHasSeenThisDog(ownerId);
    }
   /* public void userLikesThisOwnerDog(){
        createDogObject(ownerId);
        dogsLiked.collection("dogsLiked").document(ownerId).set(dogOwner);
        userHasSeenThisDog(ownerId);
    }*/
    public void userDisLikesThisOwnerDog(String ownerId){
        createDogObject(ownerId);
        dogsDisLiked.collection(dogsDisLikedCollection).document(ownerId).set(dogOwner);
        userHasSeenThisDog(ownerId);
    }

    public void userHasSeenThisDog(String ownerId){
        dogsSeen.collection(dogsSeenCollection).document(ownerId).set(dogOwner);
    }
    private void createDogObject(String ownerId){
        dogOwner.put("onwer",ownerId);
    }
}
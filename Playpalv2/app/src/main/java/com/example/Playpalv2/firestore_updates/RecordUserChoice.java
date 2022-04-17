package com.example.Playpalv2.firestore_updates;

import android.widget.Toast;

import com.example.Playpalv2.registration.Register3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RecordUserChoice {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userID;
    private String firebaseUser = auth.getCurrentUser().getUid();
    Map<String, Object> dogOwner = new HashMap<>();

    private DocumentReference dogsLiked = db.collection("Dog Owners").
            document(firebaseUser);

    private DocumentReference dogsDisLiked = db.collection("Dog Owners").
            document(firebaseUser);

    private DocumentReference dogsSeen = db.collection("Dog Owners").
            document(firebaseUser);

    public RecordUserChoice() {
        userID = firebaseUser;

    }

    public RecordUserChoice(String userID) {
        this.userID = userID;
    }

   public void userLikesThisOwnerDog(String ownerId){
        createDogObject(ownerId);
        dogsLiked.collection("dogsLiked").document(ownerId).set(dogOwner);
        userHasSeenThisDog(ownerId);
    }
   /* public void userLikesThisOwnerDog(){
        createDogObject(ownerId);
        dogsLiked.collection("dogsLiked").document(ownerId).set(dogOwner);
        userHasSeenThisDog(ownerId);
    }*/
    public void userDisLikesThisOwnerDog(String ownerId){
        createDogObject(ownerId);
        dogsDisLiked.collection("dogsDisLiked").document(ownerId).set(dogOwner);
        userHasSeenThisDog(ownerId);
    }

    public void userHasSeenThisDog(String ownerId){
        dogsSeen.collection("dogsSeen").document(ownerId).set(dogOwner);
    }
    private void createDogObject(String ownerId){
        dogOwner.put("onwer",ownerId);
    }
}
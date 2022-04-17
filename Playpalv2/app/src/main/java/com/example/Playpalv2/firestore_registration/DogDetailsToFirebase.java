package com.example.Playpalv2.firestore_registration;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Playpalv2.firestore_updates.RecordUserChoice;
import com.example.Playpalv2.registration.Reg4;
import com.example.Playpalv2.registration.Register3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DogDetailsToFirebase extends AppCompatActivity {
    private Register3 register3;
    private Reg4 register4;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private String userID;
    private boolean isSucces = false;
    private String dogId;
    Map<String, Object> dogOwner = new HashMap<>();

    private void getCurrentUser(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            userID = firebaseUser.getUid();
        }else{
            userID = "Lt7vReeyTcaqxRXuPxYKC2Aj7uC2";
            Toast.makeText(register3, "No user ID",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void recordDetails(String DogNameInput, String DogBreedInput, String DogAgeInput,
                              String DogSexInput, String DogWeightInput, String DogBioInput) {

        setDog(DogNameInput, DogBreedInput, DogAgeInput, DogSexInput,
                DogWeightInput,DogBioInput);

    }

    private void setDog(String DogNameInput, String DogBreedInput, String DogAgeInput, String DogSexInput,
                       String DogWeightInput, String DogBioInput) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            userID = firebaseUser.getUid();
        }else{
            Toast.makeText(register3, "No user ID",
                    Toast.LENGTH_LONG).show();
        }

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database

        // DocumentReference docRef = db.collection("Dog Owners").document(userID);
        CollectionReference collecRef = db.collection("Dog Breeds").
                document(DogBreedInput).collection("Dogs");
        Map<String,Object> dogInfo = new HashMap<>();
        dogInfo.put("name", DogNameInput);
        dogInfo.put("breed", DogBreedInput );
        dogInfo.put("age", Integer.parseInt(DogAgeInput));
        dogInfo.put("sex", DogSexInput);
        dogInfo.put("weight", Integer.parseInt(DogWeightInput));
        dogInfo.put("bio", DogBioInput);
        // dogInfo.put("Images","");
        dogInfo.put("owner", userID);

        DocumentReference dogsSeen = db.collection("Dog Owners").
                document(userID);
        dogOwner.put("Owner", userID);
        dogsSeen.collection("dogsSeen").document(userID).set(dogOwner);

        collecRef.add(dogInfo).addOnSuccessListener(documentReference -> {
            Log.e("THIS IS THE DOCUMENT ID", documentReference.getId());
            dogId = documentReference.getId();

          //  goToReg4(documentReference.getId(),DogBreedInput);

        }).addOnFailureListener(e -> Log.e("SOMETHING WHENT BAD", "WIKES"));

    }

    public String getDogId() {
        return dogId;
    }

    public boolean isSuccess() {
        return isSucces;
    }

   /* private void goToReg4(String dogId, String breed){

        Intent intent = new Intent(register3, register4);
        intent.putExtra("dog_id", dogId);
        intent.putExtra("breed", breed);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
*/
}

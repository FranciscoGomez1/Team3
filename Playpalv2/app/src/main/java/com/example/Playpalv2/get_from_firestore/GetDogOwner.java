package com.example.Playpalv2.get_from_firestore;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.registration.PlaypalRegister2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetDogOwner  {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private String ownerID;
    private DogOwnerModel owner;


    public GetDogOwner(String ownerId) {
        this.ownerID = ownerId;
    }

    public void getOwner(OnGotDogOwnerListener onGotDogOwnerListener){
        getDogOwner(onGotDogOwnerListener);
    }
    private void getDogOwner(OnGotDogOwnerListener onGotDogOwnerListener1){
        getTheDogOwner(onGotDogOwnerListener1);
    }
  private void getTheDogOwner(OnGotDogOwnerListener onGotDogOwnerLister){

      db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database
      DocumentReference docRef = db.collection("Dog Owners").document(ownerID);
      docRef.get().addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
              DocumentSnapshot document = task.getResult();
              if (document.exists()) {
                //  Log.e("DCCUMENT", document.toString());
                  owner = document.toObject(DogOwnerModel.class);
                  assert owner != null;
                  Log.e("GET DOGGO OWNER ID", owner.toString());

                  onGotDogOwnerLister.onGotOwner(owner);
              } else {
                  Log.d( "TEst", "No such document");
              }
          } else {
              Log.d("TEst", "get failed with ", task.getException());
          }
      });


  }

}

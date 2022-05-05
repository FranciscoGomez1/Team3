package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.models.DogOwnerModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class GetServiceProviders {

private FirebaseFirestore db = FirebaseFirestore.getInstance();
private LinkedList<DogOwnerModel> walkers = new LinkedList<>();
private int dogsTobeCheck = 0;
private int dogsCheck = 0;
private int sizeOfQuerry = 0;

private DogOwnerModel owner = new DogOwnerModel();
private String mAuth = FirebaseAuth.getInstance().getUid();

public void fetchServiceProviders(GotServiceProvidersListener gotWalkerListener1){
    //getTheServiceProviders(gotWalkerListener1);
}

/*private void getTheServiceProviders(GotServiceProvidersListener gotServiceProvidersListener) {
    *//*final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
    queryFirebeCollection("IsSitter",
            gotServiceProvidersListener);*//*
}*/
void queryFirebeCollection(String q, GotServiceProvidersListener gotServiceProvidersListener){
    db.collection("Dog Owners").whereEqualTo(q, true).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            sizeOfQuerry = queryDocumentSnapshots.size();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                owner = Objects.requireNonNull(documentSnapshot.toObject(DogOwnerModel.class));
                walkers.add(owner);
               /* if(dogsTobeCheck - 1 == dogsCheck) {
                    gotWalkerListener.onGotWalkers(walkers);
                }*/
                dogsTobeCheck++;
                if(dogsTobeCheck == sizeOfQuerry){
                    gotServiceProvidersListener.onGotServiceProviders(walkers);
                }
            }
        }
    });
}

}


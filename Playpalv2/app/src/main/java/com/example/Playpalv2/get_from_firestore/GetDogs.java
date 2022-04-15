package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.flipCards.DogModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


public class GetDogs {
    FirebaseFirestore db;
    Queue<DogModel> qDogs = new LinkedList<>();
    public void fetchDogs(OnGotDogsListener onGotDogsListener1){
        getTheDogs(onGotDogsListener1);
    }


    private void getTheDogs(OnGotDogsListener onGotDogsListener) {
//FirebaseFirestore db;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String[] doc = new String[1];
        final CollectionReference[] thisCollecRef = new CollectionReference[1];
        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database
        CollectionReference collecRef = db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs");

        collecRef.get().addOnCompleteListener(task -> {
            Log.i("TAG","ONSUCESS: IT WORKS");
            List<DocumentSnapshot> snapshotList = Objects.requireNonNull(task.getResult()).getDocuments();

            try {
                for (DocumentSnapshot snapshot : snapshotList) {
                    Log.i("TAG", Objects.requireNonNull(snapshot.getId()));
                    qDogs.add(snapshot.toObject(DogModel.class));
                    Log.i("TAG", Objects.requireNonNull(snapshot.getData()).toString());
                }
                onGotDogsListener.onGotDogs(qDogs);
            }catch(Exception e){
                Log.i("EXEPTION", e.getMessage());
            }


        });


      /*  thisCollecRef =  db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs");

        thisCollecRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.i("TAG", "ONSUCESS: IT WORKS");
                List<DocumentSnapshot> snapshotList = Objects.requireNonNull(task.getResult()).getDocuments();

                try {
                    for (DocumentSnapshot snapshot : snapshotList) {
                        Log.i("TAG", Objects.requireNonNull(snapshot.getId()));
                        qDogs.add(new DogModel(
                                Objects.requireNonNull(snapshot.get("Age")).toString(),
                                Objects.requireNonNull(snapshot.get("Bio")).toString(),
                                Objects.requireNonNull(snapshot.get("Breed")).toString(),
                                (List<String>) snapshot.get("Images"),
                                Objects.requireNonNull(snapshot.get("Name")).toString(),
                                Objects.requireNonNull(snapshot.get("Owner")).toString(),
                                Objects.requireNonNull(snapshot.get("Sex")).toString(),
                                Objects.requireNonNull(snapshot.get("Weight")).toString()));

                    }
                    onGotDogsListener.onGotDogs(qDogs);
                } catch (Exception e) {
                    Log.i("EXEPTION", e.getMessage());
                }
            }
        });*/
    }

}

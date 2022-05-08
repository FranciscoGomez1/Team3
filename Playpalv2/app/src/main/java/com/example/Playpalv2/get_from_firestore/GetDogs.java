package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.flipCards.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
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
    public void fetchDogs(OnGotDogsListener onGotDogsListener1){
        getTheDogs(onGotDogsListener1);
    }
    private DogModel dog = new DogModel();
    private String mAuth = FirebaseAuth.getInstance().getUid();

    private void getTheDogs(OnGotDogsListener onGotDogsListener) {
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();

//FirebaseFirestore db;
        final String[] doc = new String[1];
        final CollectionReference[] thisCollecRef = new CollectionReference[1];
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
                    hasSeenDog(dog, onGotDogsListener);
                    //tasks.add();//db.collection("Dog Owners").document(mAuth).collection("dogsSeen").get());
                    dogsTobeCheck++;
                }
                /*Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> task) {
                            for(DocumentSnapshot documentSnap : queryDocumentSnapshots){
                                if(documentSnap.exists()) {
                                    Log.e("The dog has been seen", documentSnap.toObject(DogModel.class).getName());
                                }else{
                                    qDogs.add(documentSnap.toObject(DogModel.class));
                                    //qDogs.add(dog);
                                    //onGotDogsListener.onGotDogs(qDogs);
                                    //dogQueueViewModel.update(qDogsClass);
                                }
                                //qDogs.add(documentSnap.toObject(DogModel.class));
                            }
                        onGotDogsListener.onGotDogs(qDogs);
                    }*/
            }
        });

/*
                addOnSuccessListener(new OnSucessListener<QuerySnapshot>() {
                    @Override
                    public void On

            try {
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                    Log.i("TAG", Objects.requireNonNull(snapshot.getId()));
                    dog = snapshot.toObject(DogModel.class);
                     = db.collection("Dog Owners").document(mAuth).
                            collection("dogsSeen").document(dog.getOwner()).get();
                    tasks.add(q.get());
                    Log.i("TAG", Objects.requireNonNull(snapshot.getData()).toString());
                }
                getNotSeenDogs(tasks);
               // onGotDogsListener.onGotDogs(qDogs);
            }catch(Exception e){
                Log.i("EXEPTION", e.getMessage());
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("WHAT WHENT WRONG?", e.toString());
            }
        });

*/

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

    private void hasSeenDog(DogModel dog, OnGotDogsListener onGotDogsListener) {

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
                       qDogs.add(dog);
                       //addToQ(dog, onGotDogsListener);


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
    void gotAllDgos(OnGotDogsListener onGotDogsListener){

    }
 /* private void getNotSeenDogs(List<Task<DocumentSnapshot>> tasks) {
        Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
            @Override
            public void onComplete(@NonNull Task<List<Task<?>>> task) {
                for(Task<DocumentSnapshot> mtask : tasks){


                }
            }
        });
    }*/
}

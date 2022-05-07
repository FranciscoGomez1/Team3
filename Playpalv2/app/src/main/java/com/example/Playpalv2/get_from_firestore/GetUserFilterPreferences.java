package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.UserFilterPreferences;
import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.models.DogOwnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class GetUserFilterPreferences {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private String userId = auth.getCurrentUser().getUid();

    private UserFilterPreferences userFilterPreferences = new UserFilterPreferences();

    public GetUserFilterPreferences() {

    }

    public void getUserPreferences( OnGotUserPreferences onGotUserPreferences){
        Log.e("USERID", userId);
        DocumentReference docRef = db.collection("Dog Owners").document(userId).
                collection("Preference").document(userId+"preference");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isComplete()){
                    DocumentSnapshot ds = task.getResult();
                    userFilterPreferences = ds.toObject(UserFilterPreferences.class);
                    onGotUserPreferences.onGotPreferences(userFilterPreferences);

                }
            }
        });

    }

}

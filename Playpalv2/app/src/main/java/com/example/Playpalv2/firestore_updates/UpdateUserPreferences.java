package com.example.Playpalv2.firestore_updates;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserPreferences {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    Map<String,Object> userFilterPreferences = new HashMap<>();

    private String userID = auth.getCurrentUser().getUid();

    public UpdateUserPreferences(Map<String, Object> userFilterPreferences) {
        this.userFilterPreferences = userFilterPreferences;
    }

    public void updateUserPreferenceFirebase(){
        userID = "PTE2QeXsVSVEDEwiY5FFvbyIxM23";

        db.collection("Dog Owners").document(userID).
                collection("Preference").document(userID+"preference").
                set(userFilterPreferences);
    }
}

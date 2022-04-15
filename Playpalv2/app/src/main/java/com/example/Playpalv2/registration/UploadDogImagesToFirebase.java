package com.example.Playpalv2.registration;

import android.app.Activity;
import android.net.Uri;

import com.example.Playpalv2.imagesToFirestore.ImagesToFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UploadDogImagesToFirebase extends ImagesToFirestore{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private String dogId;
    private String breed;
    private Uri[] dogImages = new Uri[4];

    ImagesToFirestore imagesToFirestore;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    private Activity reg4;

    public UploadDogImagesToFirebase(Uri[] dogImages, String breed, String dogId, Activity reg4) {
        super(dogImages, reg4);
        this.dogId = dogId;
        this.breed = breed;
        this.dogImages = dogImages;
        this.reg4 = reg4;
        imagesToFirestore = new ImagesToFirestore(dogImages, reg4);
    }

    public void setUserImagesLinksUrls(){
        setUrls();
    }

     void setUrls() {
         List<String> imagesList = Arrays.asList(getimagesUrls());
         FirebaseUser firebaseUser = auth.getCurrentUser();
         if (firebaseUser != null) {
             //userID = firebaseUser.getUid();
         } else {
            /*Toast.makeText(Reg4.this, "No user ID",
                    Toast.LENGTH_LONG).show();*/
         }

         db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database

         // DocumentReference docRef = db.collection("Dog Owners").document(userID);
         DocumentReference docRef = db.collection("Dog Breeds").
                 document(breed).collection("Dogs").document(dogId);
         Map<String, Object> Images = new HashMap<>();
         Images.put("images", imagesList);


         docRef.update(Images).addOnCompleteListener(task -> {
             if (task.isSuccessful()) {
                 // firebaseUser.sendEmailVerification();

             } else {
                 try {
                     throw Objects.requireNonNull(task.getException());
                 } catch (Exception e) {
                   /* Log.e(TAG, e.getMessage());
                    Toast.makeText(PlaypalRegister2.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();*/
                 }
             }
         });
     }
}

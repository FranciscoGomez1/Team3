package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.Playpalv2.PlaypalRegister2;
import com.example.Playpalv2.Register3;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ImagesToFirestore  {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private String dogId;
    private String breed;
    private Uri[] dogImages = new Uri[4];
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();;
    private String[] urlsImages = new String[4];
    int position = 0;

    public ImagesToFirestore(Uri[] dogImages, String breed, String dogId) {
        this.dogImages = dogImages;
        this.breed = breed;
        this.dogId = dogId;
    }

    public void setDogImages(Uri[] dogImages) {
        this.dogImages = dogImages;
    }

    public void uploadImages() {
        for(Uri i: dogImages){
            uploadImage(i);
        }
        Log.e("IS IS FASTER", ":(");
    }

    private void uploadImage(Uri dogImage) {
        String image;
        final String randomKey = UUID.randomUUID().toString();
        StorageReference file = storageReference.child("images/" + randomKey);
        file.putFile(dogImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        file.getDownloadUrl().addOnSuccessListener(uri -> addUrl(uri.toString()));
                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("WHAT HAPPEN:", "FIRESTORE");

                    }
                });
        Log.e("IMAGES TO BE UPLOADED", file.toString());
        //getReference("images/"+fileName);
    }

    void addUrl(String image){
        urlsImages[position++] = image;
        Log.e("WHAT HAPPEN URI:", image );
    }

    public void addImagesOfDogToFirebase(){
        addImagesToDoggo();
    }

    private void addImagesToDoggo() {
        List<String> imagesList = Arrays.asList(urlsImages);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            //userID = firebaseUser.getUid();
        }else{
            /*Toast.makeText(Reg4.this, "No user ID",
                    Toast.LENGTH_LONG).show();*/
        }

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database

        // DocumentReference docRef = db.collection("Dog Owners").document(userID);
        DocumentReference docRef = db.collection("Dog Breeds").
                document(breed).collection("Dogs").document(dogId);
        Map<String,Object> Images = new HashMap<>();
        Images.put("Images", imagesList );


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

package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.Playpalv2.databinding.ActivityReg4Binding;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.ImagesToFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Reg4 extends AppCompatActivity {

    private static final int PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE = 4;
    private static final int REQUEST_PHOTO_PICKER_SINGLE_SELECT = 1;
    private static final int REQUEST_PHOTO_PICKER_MULTI_SELECT = 4 ;

    final int maxNumPhotosAndVideos = 10;

    private Button pickBtn;
    private Button nextBtn;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    String userID;

    ImagesToFirestore imagesToFirestore;
    Uri[] dogImages = new Uri[4];
    ActivityReg4Binding binding;
    private String[] urlsImages = new String[4];

    private static final String TAG= "Reg4";


    /*private FirebaseStorage storage;
    private StorageReference storageReference;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityReg4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent mintent = getIntent();
        String dogId = mintent.getExtras().getString("dogId");
        String breed = mintent.getExtras().getString("breed");
        Log.e("PLEASE GOD", dogId);
        Log.e("PLEASE GOD", breed);

        imagesToFirestore = new ImagesToFirestore(dogImages,breed,dogId);
        nextBtn = findViewById(R.id.btn_next4);

        pickBtn = findViewById(R.id.pick_photos_button);

        /*storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();*/

        pickBtn.setOnClickListener(View -> {

            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNumPhotosAndVideos);
            startActivityForResult(intent, PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE);

        });

        GridAdapter gridAdapter1 = new GridAdapter(Reg4.this,dogImages);
        binding.gridView.setAdapter(gridAdapter1);


        nextBtn.setOnClickListener(View -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            imagesToFirestore.addImagesOfDogToFirebase();
            setDogReferenceToDogOwner(dogId, breed);
            finish();
        });

    }
    // onActivityResult() handles callbacks from the photo picker.

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Reg4.RESULT_OK) {
            // Handle error
            return;
        }

        switch (requestCode) {
            case REQUEST_PHOTO_PICKER_SINGLE_SELECT:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                Log.e("works", "workds");
                // Do stuff with the photo/video URI.
                return;
            case REQUEST_PHOTO_PICKER_MULTI_SELECT:
                // Get photo picker response for multi select
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri currentUri2 = data.getClipData().getItemAt(i).getUri();
                    dogImages[i] = currentUri2;
                    Log.e("works", "workdssssssssssssssssssssssss");

                    GridAdapter gridAdapter = new GridAdapter(Reg4.this,dogImages);
                    binding.gridView.setAdapter(gridAdapter);


                    // Do stuff with each photo/video URI.
                }
                imagesToFirestore.uploadImages();

                //uploadImages();
                Log.e("THANK GOD IT WORKS", dogImages[1].toString());
                return;


        }
    }
    void setDogReferenceToDogOwner(String dogId, String dogBreed){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
           userID = firebaseUser.getUid();
        }else{
            Toast.makeText(Reg4.this, "No user ID",
                    Toast.LENGTH_LONG).show();
        }

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database


        DocumentReference docRef = db.collection("Dog Owners").document(userID);

        //Set a hashmap to pass data to the database
        Map<String,Object> dogOwner = new HashMap<>();
        dogOwner.put("DogId", dogId);
        dogOwner.put("DogsBreed", dogBreed);


        docRef.update(dogOwner).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // firebaseUser.sendEmailVerification();

            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Reg4.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    };


    private void uploadImages() {
      /*imagesToFirestore.setDogImages(dogImages);*/
       /* for(Uri i: dogImages){
            uploadImage(i);
        }*/
    }
/*
    private void uploadImage(Uri dogImage) {
                 String image;
               final String randomKey = UUID.randomUUID().toString();
                StorageReference file = storageReference.child("images/" + randomKey);
                file.putFile(dogImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               file.getDownloadUrl().addOnSuccessListener(uri -> checkTest(uri.toString()));
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

    void checkTest(String image){
        Log.e("WHAT HAPPEN URI:", image );
    }*/
}
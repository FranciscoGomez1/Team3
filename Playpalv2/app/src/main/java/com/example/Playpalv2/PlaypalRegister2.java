package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Playpalv2.flipCards.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class PlaypalRegister2 extends AppCompatActivity {
    // Set the android widgets
    private Button registerButtomPage2;
    private EditText dogOwnerBio;
    private String userID;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db;

    private static final String TAG= "PlaypalRegister2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpal_register2);

        registerButtomPage2 = findViewById(R.id.btn_register_page_2);
        dogOwnerBio = findViewById(R.id.dog_owner_bio);

        registerButtomPage2.setOnClickListener( view ->{

            String inputDogOwnerBio = dogOwnerBio.getText().toString();
            setDogOwnerBio(inputDogOwnerBio);

        });


    }

    private void setDogOwnerBio(String inputDogOwnerBio) {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            userID = firebaseUser.getUid();
        }else{
            Toast.makeText(PlaypalRegister2.this, "No user ID",
                    Toast.LENGTH_LONG).show();
        }

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database


        DocumentReference docRef = db.collection("Dog Owners").document(userID);

        //Set a hashmap to pass data to the database
        Map<String,Object> userBio = new HashMap<>();
        userBio.put("Bio", inputDogOwnerBio);


        docRef.update(userBio).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // firebaseUser.sendEmailVerification();
                Intent intent = new Intent(PlaypalRegister2.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(PlaypalRegister2.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
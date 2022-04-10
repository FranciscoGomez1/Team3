package com.example.Playpalv2.registrationClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Playpalv2.R;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DropOutMenusReg3;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.RegisterPage3;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register3 extends AppCompatActivity {

    // create a registerpage3 object use to send to firebase
    private RegisterPage3 registerPage3 = new RegisterPage3();
    // create  a dropoutMenusReg3 object to use in this actvity
    private DropOutMenusReg3 dropOutMenusReg3 = new DropOutMenusReg3();
    //
    private Button nextBtn;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db;
    private String userID;

    private EditText DogName;
    private MaterialAutoCompleteTextView DogBreed;
    private MaterialAutoCompleteTextView DogAge;
    private MaterialAutoCompleteTextView DogSex;
    private MaterialAutoCompleteTextView DogWeight;
    private EditText DogBio;

    private static final String TAG= "Register3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        nextBtn = findViewById(R.id.btn_next3);
        DogName = findViewById(R.id.dog_name);
        DogBreed = findViewById(R.id.breeds_list);
        DogAge = findViewById(R.id.dog_age_list);
        DogSex = findViewById(R.id.dog_age_list);
        DogWeight = findViewById(R.id.dog_weight_list);
        DogBio = findViewById(R.id.dog_bio);

        //Auto complete drop out menu for dog breeds
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dropOutMenusReg3.getBreeds());
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.breeds_list);
        textView.setAdapter(adapter);

        //Auto complete drop out menu for dog age
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dropOutMenusReg3.getAgeList());
        AutoCompleteTextView textViewAge = (AutoCompleteTextView)
                findViewById(R.id.dog_age_list);
        textViewAge.setAdapter(ageAdapter);

        //Auto complete drop out menu for dog sex
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dropOutMenusReg3.getSexList());
        AutoCompleteTextView textViewSex = (AutoCompleteTextView)
                findViewById(R.id.dog_sex_list);
        textViewSex.setAdapter(sexAdapter);

        //Auto complete drop out menu for dog wight
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dropOutMenusReg3.getWeightList());
        AutoCompleteTextView textViewWeight = (AutoCompleteTextView)
                findViewById(R.id.dog_weight_list);
        textViewWeight.setAdapter(weightAdapter);


        nextBtn.setOnClickListener(View -> {
            String DogNameInput = DogName.getText().toString();
            String DogBreedInput = DogBreed.getText().toString();
            String DogAgeInput = DogAge.getText().toString();
            String DogSexInput = DogSex.getText().toString();
            String DogWeightInput = DogWeight.getText().toString();
            String DogBioInput = DogBio.getText().toString();

            setDog(DogNameInput, DogBreedInput, DogAgeInput, DogSexInput,
                    DogWeightInput,DogBioInput);

        });

    }
    private void setDog(String DogNameInput, String DogBreedInput, String DogAgeInput, String DogSexInput,
                        String DogWeightInput, String DogBioInput) {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            userID = firebaseUser.getUid();
        }else{
            Toast.makeText(Register3.this, "No user ID",
                    Toast.LENGTH_LONG).show();
        }

        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database

       // DocumentReference docRef = db.collection("Dog Owners").document(userID);
        CollectionReference collecRef = db.collection("Dog Breeds").
                document(DogBreedInput).collection("Dogs");
        Map<String,Object> dogInfo = new HashMap<>();
        dogInfo.put("Name", DogNameInput);
        dogInfo.put("Breed", DogBreedInput );
        dogInfo.put("Age", DogAgeInput);
        dogInfo.put("Sex", DogSexInput);
        dogInfo.put("Weight", DogWeightInput);
        dogInfo.put("Bio", DogBioInput);
       // dogInfo.put("Images","");
        dogInfo.put("Owner", userID);


        collecRef.add(dogInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.e("THIS IS THE DOCUMENT ID", documentReference.getId());
                goToReg4(documentReference.getId(),DogBreedInput);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("SOMETHING WHENT BAD", "WIKES");

            }
        });
    }

    private void goToReg4(String dogId, String breed){

        Intent intent = new Intent(this, Reg4.class);
        intent.putExtra("dogId", dogId);
        intent.putExtra("breed", breed);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
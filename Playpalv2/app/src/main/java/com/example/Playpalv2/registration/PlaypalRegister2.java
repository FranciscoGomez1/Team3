package com.example.Playpalv2.registration;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Playpalv2.R;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DOB;
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

    private DatePickerDialog datePickerDiaglog;

    private Button pickDateBtn;

    private DOB dob;

    private String date;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;

    private static final String TAG= "PlaypalRegister2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpal_register2);
        dob = new DOB();
         pickDateBtn = findViewById(R.id.date_picker);
         pickDateBtn.setText(dob.getTodaysDate());

        registerButtomPage2 = findViewById(R.id.btn_register_page_2);
        dogOwnerBio = findViewById(R.id.dog_owner_bio);

        initDatePicker();
         //pickDateBtn.setOnClickListener(View -> openDatePicker(this));

        registerButtomPage2.setOnClickListener( view ->{

            String owerDOB =  pickDateBtn.toString();
            String inputDogOwnerBio = dogOwnerBio.getText().toString();
            setDogOwnerBio(inputDogOwnerBio, "test");
            Intent intent = new Intent(PlaypalRegister2.this, UserUploadsImages.class);
            intent.putExtra("user_id", userID);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();

        });

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            dob.selectDOB(day,month, year);
            date = dob.getDate();
             pickDateBtn.setText(date);
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDiaglog = new DatePickerDialog(this, style, dateSetListener, dob.getYear(),
                dob.getMonth(), dob.getDay());
        datePickerDiaglog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDogOwnerBio(String inputDogOwnerBio, String DOB) {

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
        Map<String,Object> userDob = new HashMap<>();
        userBio.put("bio", inputDogOwnerBio);
        userDob.put("dob", inputDogOwnerBio);


        docRef.update(userBio).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // firebaseUser.sendEmailVerification();

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
    public void openDatePicker(PlaypalRegister2 view){
        datePickerDiaglog.show();
    }
}
package com.example.Playpalv2.registration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Playpalv2.date_and_time.DateAndTime;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.LogIn;
import com.example.Playpalv2.R;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlaypalRegister extends AppCompatActivity {

    //Set the android widgets
    private Button btnRegister; //Sign up button
    private Button btnSignIn; //Sign in to home
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText confirmPassword;

    private String geoHash;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;

    private String userID;
    private String dateTime;

    FirebaseFirestore db;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "PlaypalRegistration";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpal_registration);

        btnRegister = findViewById(R.id.btn_register); // Link to the xml bottom element
        //btnSignIn = findViewById(R.id.button_sign_in); //link to sign in

        //Get the date of registratio
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        getLocation(fusedLocationClient);

        // Set a clickListner
        btnRegister.setOnClickListener(view -> { // When clicked it takes user to the main activity

            //Get the user input data
            String inputFirtName = firstName.getText().toString();
            String inputLastName = lastName.getText().toString();
            String inputEmail = email.getText().toString();
            String inputMobile = mobile.getText().toString();
            String inputPassword = password.getText().toString();
            String inputConfirmPassword = confirmPassword.getText().toString();

            RegistrationPage1 registrationPage1 = new RegistrationPage1(inputFirtName, inputLastName, inputEmail, inputMobile, inputPassword, inputConfirmPassword);
            //goToReg2Page();
            if (registrationPage1.isRegisterInfoValid()) {
                registerToFirebase(inputFirtName, inputLastName, inputEmail, inputMobile, inputPassword);
            }
        });

        // Set a clickListner
      /*  btnSignIn.setOnClickListener(view -> { // When clicked it takes user to the main activity
            // firebaseUser.sendEmailVerification();
           goToLogIn();
        });*/
    }

    private boolean registerUser(String inputFirtName, String inputLastName, String inputEmail, String inputMobile, String inputPassword) {

        return !inputFirtName.isEmpty() && !inputLastName.isEmpty() && !inputEmail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
                && !inputMobile.isEmpty() && !inputPassword.isEmpty();

    }


    // Function to register a user to firebase
    private void registerToFirebase(String inputFirtName, String inputLastName, String inputEmail,
                                    String inputMobile, String inputPassword) {

        mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(PlaypalRegister.this,
                task -> {
                    if (task.isSuccessful()) { // if the user authentication was a success no errors with the
                        // password or email

                        FirebaseUser firebaseUser = mAuth.getCurrentUser(); // Get the current user
                        if (firebaseUser != null) {
                            userID = firebaseUser.getUid();
                        } else {
                            Toast.makeText(PlaypalRegister.this, "No user ID",
                                    Toast.LENGTH_LONG).show();
                        }

                        DateAndTime dateAndTime = new DateAndTime();

                        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database


                        DocumentReference docRef = db.collection("Dog Owners").document(userID);

                        // Compute the GeoHash for a lat/lng point
                        getLocation(fusedLocationClient);
                        double lat = 51.5074;
                        double lng = 0.1278;
                        geoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));

                        //Set a hashmap to pass the user info to firestore
                        //private Map<Integer, String> map = new HashMap<>();
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("id", userID);
                        userInfo.put("first_name", inputFirtName);
                        userInfo.put("last_name", inputLastName);
                        userInfo.put("email", inputEmail);
                        userInfo.put("mobile_number", inputMobile);
                        userInfo.put("account_created", dateAndTime.getCurrenTime().toString());
                        userInfo.put("has_walker", "");
                        userInfo.put("has_sitter", "");
                        userInfo.put("dogs_seen", "");
                        userInfo.put("dogs_liked", "");
                        userInfo.put("dogs_disLiked", "");
                        userInfo.put("location", geoHash);


                        docRef.set(userInfo).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {

                                goToReg2Page();
                                // firebaseUser.sendEmailVerification();
                                /*Intent intent = new Intent(PlaypalRegister.this, PlaypalRegister2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();*/

                            } else {
                                try {
                                    throw Objects.requireNonNull(task.getException());
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    Toast.makeText(PlaypalRegister.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else { //Else if there is an error  with the password or email when the user tries to register.

                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthWeakPasswordException e) {
                            password.setError("Password is week");
                            password.requestFocus();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            email.setError("Invalid email or already in use");
                            email.requestFocus();
                        } catch (FirebaseAuthUserCollisionException e) {
                            email.setError("User already register with this email");
                            email.requestFocus();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(PlaypalRegister.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                }
        );

    }

    private void goToReg2Page() {
        //Intent intent = new Intent(PlaypalRegistration.this, PlaypalRegister2.class);
        Intent intent = new Intent(PlaypalRegister.this, PlaypalRegister2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    private void goToLogIn() {
        Intent intent = new Intent(PlaypalRegister.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    private void getLocation(FusedLocationProviderClient fusedLocationClient) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    userLocation = location;
                    if (location != null) {
                        // Logic to handle location object
                    }
                });

    }

}

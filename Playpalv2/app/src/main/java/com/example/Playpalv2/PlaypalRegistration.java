package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlaypalRegistration extends AppCompatActivity {

    //Set the android widgets
    private Button btnRegister; //Sign up button
    private Button btnSignIn; //Sign in to home
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText confirmPassword;

    private String userID;

    FirebaseFirestore db;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG= "PlaypalRegistration";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpal_registration);

        btnRegister = findViewById(R.id.btn_register); // Link to the xml bottom element
        btnSignIn = findViewById(R.id.button_sign_in); //link to sign in

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);

        // Set a clickListner
        btnRegister.setOnClickListener(view -> { // When clicked it takes user to the main activity

            //Get the user input data
            String inputFirtName = firstName.getText().toString();
            String inputLastName = lastName.getText().toString();
            String inputEmail = email.getText().toString();
            String inputMobile = mobile.getText().toString();
            String inputPassword = password.getText().toString();
            String inputConfirmPassword = confirmPassword.getText().toString();

            registerUser(inputFirtName, inputLastName, inputEmail, inputMobile, inputPassword );

        });

        // Set a clickListner
        btnSignIn.setOnClickListener(view -> { // When clicked it takes user to the main activity
            startActivity(new Intent(PlaypalRegistration.this, MainActivity.class));
        });
    }

    // Function to register a user to firebase
    private void registerUser(String inputFirtName, String inputLastName, String inputEmail,
                              String inputMobile, String inputPassword) {

        mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(PlaypalRegistration.this,
                task -> {
                    if(task.isSuccessful()){ // if the user authentication was a success no errors with the
                        // password or email

                        FirebaseUser firebaseUser = mAuth.getCurrentUser(); // Get the current user
                        if(firebaseUser != null){
                            userID = firebaseUser.getUid();
                        }else{
                            Toast.makeText(PlaypalRegistration.this, "No user ID",
                                    Toast.LENGTH_LONG).show();
                        }

                        db = FirebaseFirestore.getInstance(); // Get an instance of the firestore database


                        DocumentReference docRef = db.collection("Dog Owners").document(userID);

                        //Set a hashmap to pass the user info to firestore
                        //private Map<Integer, String> map = new HashMap<>();
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("ID", userID);
                        userInfo.put("Fist Name", inputFirtName);
                        userInfo.put("Last Name", inputLastName);
                        userInfo.put("Email", inputEmail);
                        userInfo.put("Mobile Number", inputMobile);

                        docRef.set(userInfo).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){

                                // firebaseUser.sendEmailVerification();
                                Intent intent = new Intent(PlaypalRegistration.this, PlaypalRegister2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();

                            }else{
                                try{
                                    throw Objects.requireNonNull(task.getException());
                                }catch (Exception e){
                                    Log.e(TAG, e.getMessage());
                                    Toast.makeText(PlaypalRegistration.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }else{ //Else if there is an error  with the password or email when the user tries to register.

                        try{
                            throw Objects.requireNonNull(task.getException());
                        }catch(FirebaseAuthWeakPasswordException e){
                            password.setError("Password is week");
                            password.requestFocus();
                        }catch (FirebaseAuthInvalidCredentialsException e) {
                            email.setError("Invalid email or already in use");
                            email.requestFocus();
                        }catch (FirebaseAuthUserCollisionException e){
                            email.setError("User already register with this email");
                            email.requestFocus();
                        }catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(PlaypalRegistration.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                }

        );

    }

}
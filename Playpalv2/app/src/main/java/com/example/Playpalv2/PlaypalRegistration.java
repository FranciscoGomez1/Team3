package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaypalRegistration extends AppCompatActivity {

   Button btnRegister; //Sign up button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpal_registration);

        btnRegister = findViewById(R.id.btn_register); // Link to the xml bottom element

       btnRegister.setOnClickListener(new View.OnClickListener() { // Set a clickListner
           @Override
           public void onClick(View view) { // When clicked it takes user to the main activity
               startActivity(new Intent( PlaypalRegistration.this, MainActivity.class));
           }
       });
    }
}
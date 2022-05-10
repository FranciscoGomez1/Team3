package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Playpalv2.R;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.registration.PlaypalRegister;
import com.google.firebase.auth.FirebaseAuth;

import java.time.ZoneId;

public class LogIn extends AppCompatActivity {
    private Button logIn;
    private TextView register;
    private ZoneId zoneId;
    private String email;
    private String password;
    private EditText inputEmail;
    private EditText inputPassword;
    // Initialize Firebase Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);
        inputPassword = findViewById(R.id.password);
        inputEmail = findViewById(R.id.log_in_email);



        logIn.setOnClickListener(View ->{
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            //TODO TESTING
            //signIn("bripriscilla@email.com","123456"); // <---- COMMENT THIS OUT
            signIn(email, password);
        });

        register.setOnClickListener(View -> {
            Intent intent = new Intent(LogIn.this, PlaypalRegister.class);
            startActivity(intent);

        });

    }
    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogIn.this, "Authentication success.",
                                Toast.LENGTH_SHORT).show();   // Sign in success, update UI with the signed-in user's information
                        goToMain();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogIn.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END sign_in_with_email]
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
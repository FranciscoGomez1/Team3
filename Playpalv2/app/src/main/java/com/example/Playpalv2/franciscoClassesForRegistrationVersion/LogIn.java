package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.Playpalv2.R;
import com.example.Playpalv2.flipCards.MainActivity;

public class LogIn extends AppCompatActivity {

    private Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn = findViewById(R.id.login_btn);

        logIn.setOnClickListener(View ->{
            Intent intent = new Intent(LogIn.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }
}
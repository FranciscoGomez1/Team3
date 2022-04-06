package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DropOutMenusReg3;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.RegisterPage3;

public class Register3 extends AppCompatActivity {

    // create a registerpage3 object use to send to firebase
    private RegisterPage3 registerPage3 = new RegisterPage3();
    // create  a dropoutMenusReg3 object to use in this actvity
    private DropOutMenusReg3 dropOutMenusReg3 = new DropOutMenusReg3();
    //
    private Button nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        nextBtn = findViewById(R.id.btn_next3);

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
            Intent intent = new Intent(this, Reg4.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }



}
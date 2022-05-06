package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DogBreeds;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DropOutMenusReg3;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class FilterOptions extends AppCompatActivity {
    private RangeSlider ageRangeSlider;
    private RangeSlider weightSlider;
    private RangeSlider energySlider;
    private Slider distanceSlider;

    private String minAge;
    private String maxAge;

    private String minEnergy;
    private String maxEnergy;

    private String minWeight;
    private String maxWeight;

    private String minDistance;
    private String maxDistance;

    private Button goToMainBtn;

    private String sexPreference;

    private List<Float> ageValues;
    private List<Float> weightValues;
    private List<Float> energyValues;
    private DropOutMenusReg3 dropOutMenusReg3 = new DropOutMenusReg3();
    private MaterialAutoCompleteTextView DogBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_options);
        ageRangeSlider = findViewById(R.id.age_range_slider);
        DogBreed = findViewById(R.id.breeds_list);

        goToMainBtn = findViewById(R.id.btn_to_main);
        weightSlider = findViewById(R.id.weight_range_slider);
        energySlider = findViewById(R.id.energy_range_slider);
        distanceSlider = findViewById(R.id.dog_distance_range_slider);

        //Auto complete drop out menu for dog breeds

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dropOutMenusReg3.getBreeds());
        AutoCompleteTextView textView =  DogBreed;
        textView.setAdapter(adapter);

        ageRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
               ageValues = ageRangeSlider.getValues();
                Log.e("onStopTrackingTouch From", ageValues.get(0).toString());
                Log.e("onStopTrackingTouch T0", ageValues.get(1).toString());
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                ageValues = ageRangeSlider.getValues();
                minAge = ageValues.get(0).toString();
                maxAge = ageValues.get(1).toString();
                Log.e("onStopTrackingTouch From", ageValues.get(0).toString());
                Log.e("onStopTrackingTouch T0", ageValues.get(1).toString());
            }
        });

        weightSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                weightValues= ageRangeSlider.getValues();

                Log.e("onStopTrackingTouch From",weightValues.get(0).toString() );
                Log.e("onStopTrackingTouch T0", weightValues.get(1).toString());
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                weightValues = weightSlider.getValues();
                minWeight = weightValues.get(0).toString();
                maxWeight = weightValues.get(1).toString();
                Log.e("onStopTrackingTouch From",weightValues.get(0).toString() );
                Log.e("onStopTrackingTouch T0", weightValues.get(1).toString());
            }
        });

        energySlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                energyValues = energySlider.getValues();

                Log.e("onStopTrackingTouch From", energyValues.get(1).toString());
                Log.e("onStopTrackingTouch T0", energyValues.get(1).toString());
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                energyValues = energySlider.getValues();
                minEnergy = energyValues.get(0).toString();
                maxEnergy = energyValues.get(0).toString();
                Log.e("onStopTrackingTouch From", energyValues.get(1).toString());
                Log.e("onStopTrackingTouch T0", energyValues.get(1).toString());
            }
        });

        goToMainBtn.setOnClickListener(View ->{
            Log.e("Minage", minAge);
            Log.e("Maxage", maxAge);

            Log.e("MinWeight", minWeight);
            Log.e("MaxWeight", maxWeight);

            Log.e("MinEnergy", minEnergy);
            Log.e("MaxEnergy", maxEnergy);

            Log.e("Sex", sexPreference);



        });


    }
    //for dog sex filtering
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.all:
                if (checked)
                    sexPreference = "all";
                    break;
            case R.id.radio_females:
                if (checked)
                    sexPreference = "females";
                    break;
            case R.id.radio_males:
                if(checked)
                    sexPreference = "males";
                    break;

        }
    }
}
package com.example.Playpalv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Playpalv2.firestore_updates.UpdateUserPreferences;
import com.example.Playpalv2.flipCards.MainActivity;
import com.example.Playpalv2.franciscoClassesForRegistrationVersion.DropOutMenusReg3;
import com.example.Playpalv2.get_from_firestore.GetUserFilterPreferences;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterOptions extends AppCompatActivity {
    private RangeSlider ageRangeSlider;
    private RangeSlider weightSlider;
    private RangeSlider energySlider;
    private Slider distanceSlider;

    private Integer minAge;
    private Integer maxAge;

    private Integer minEnergy;
    private Integer maxEnergy;

    private Integer minWeight;
    private Integer maxWeight;

    private Float maxDistanceFloat;
    private Integer maxDistance;

    private Button goToMainBtn;

    private String sexPreference;

    private List<Float> ageValues;
    private List<Float> weightValues;
    private List<Float> energyValues;
    private Float distanceValues;
    private DropOutMenusReg3 dropOutMenusReg3 = new DropOutMenusReg3();
    private UserFilterPreferences  userFilterPreferences;
    private MaterialAutoCompleteTextView DogBreed;

    Map<String,Object> userFilterPreferencesHashMap = new HashMap<>();

    UpdateUserPreferences updateUserPreferences;
    GetUserFilterPreferences getUserFilterPreferences = new GetUserFilterPreferences();

    private String dogBreedInput;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            //This is if we coming from the drawer and not during registration
            Intent mintent = getIntent();
            String isEdit = mintent.getExtras().toString();
            //String isEdit = mintent.geExtras("isEdit");
            //Preference class
            getUserFilterPreferences.getUserPreferences(userFilterPreferences -> {
                ifIsEditPreference(userFilterPreferences);
            });

        }catch (Exception e){
            Log.e("exeption", e.toString());
        }

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
                getAgeValues();
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                getAgeValues();
            }
        });

        weightSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                getWeightValues();
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                getWeightValues();

            }
        });

        energySlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
               getEnergyValues();
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
               getEnergyValues();
            }
        });

        distanceSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
               getDistance();

            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                getDistance();

            }
        });

        goToMainBtn.setOnClickListener(View ->{
            checkFilterPreferenceAgain();

            dogBreedInput = DogBreed.getText().toString();
            Log.e("Minage", minAge.toString());
            Log.e("Maxage", maxAge.toString());

            Log.e("MinWeight", minWeight.toString());
            Log.e("MaxWeight", maxWeight.toString());

            Log.e("MinEnergy", minEnergy.toString());
            Log.e("MaxEnergy", maxEnergy.toString());

            //Log.e("Sex", sexPreference);

            userFilterPreferencesHashMap.put("MinAge", minAge);
            userFilterPreferencesHashMap.put("MaxAge", maxAge);
            userFilterPreferencesHashMap.put("MinWeight", minWeight);
            userFilterPreferencesHashMap.put("MaxWeight", maxWeight);
            userFilterPreferencesHashMap.put("MinEnergy", minEnergy);
            userFilterPreferencesHashMap.put("MaxEnergy", maxEnergy);
            userFilterPreferencesHashMap.put("Sex", sexPreference);
            userFilterPreferencesHashMap.put("Breed", dogBreedInput);
            userFilterPreferencesHashMap.put("MaxDistance", maxDistance);

            updateUserPreferences = new UpdateUserPreferences(userFilterPreferencesHashMap);
            updateUserPreferences.updateUserPreferenceFirebase();
            goToFilterActivity();
        });


    }

    private void ifIsEditPreference(UserFilterPreferences userFilterPreferences) {
        List<Float> newAgeValues = new ArrayList<Float>();
        List<Float> newEnergyValues = new ArrayList<Float>();
        List<Float> newWeightValues = new ArrayList<Float>();


        newAgeValues.add(userFilterPreferences.getMinAge().floatValue());
        newAgeValues.add(userFilterPreferences.getMaxAge().floatValue());

        newEnergyValues.add(userFilterPreferences.getMinEnergy().floatValue());
        newEnergyValues.add(userFilterPreferences.getMaxEnergy().floatValue());

        newWeightValues.add(userFilterPreferences.getMinWeight().floatValue());
        newWeightValues.add(userFilterPreferences.getMaxWeight().floatValue());

        maxDistanceFloat = userFilterPreferences.getMaxDistance().floatValue();

        ageRangeSlider.setValues(newAgeValues);
        weightSlider.setValues(newWeightValues);
        energySlider.setValues(newEnergyValues);
        distanceSlider.setValue(maxDistanceFloat);
        setBreedPreference(userFilterPreferences.getBreed());
        setRadioButtonSelection(userFilterPreferences.getSex());
        sexPreference = userFilterPreferences.getSex();

    }

    private void setBreedPreference(String breed) {
        DogBreed.setText(breed);
    }

    private void checkFilterPreferenceAgain() {
        getAgeValues();
        getEnergyValues();
        getWeightValues();
        getDistance();


    }



    private void getAgeValues() {
        ageValues = ageRangeSlider.getValues();
        minAge = Math.round(ageValues.get(0));
        maxAge = Math.round(ageValues.get(1));
    }


    private void getEnergyValues() {

        energyValues = energySlider.getValues();
        minEnergy = Math.round(energyValues.get(0));
        maxEnergy = Math.round(energyValues.get(1));

    }

    private void getDistance(){
        maxDistance = Math.round(distanceSlider.getValue());
    }

    private void getWeightValues(){
        weightValues = weightSlider.getValues();
        minWeight = Math.round(weightValues.get(0));
        maxWeight = Math.round(weightValues.get(1));
    }


    private void setRadioButtonSelection(String sex) {
        if(sex.equals("All")){
            RadioButton rb1 = (RadioButton) findViewById(R.id.all);
            rb1.setChecked(true);
        }else if (sex.equals("Male")){
            RadioButton rb1 = (RadioButton) findViewById(R.id.radio_males);
            rb1.setChecked(true);

        }else if (sex.equals("Female")){
            RadioButton rb1 = (RadioButton) findViewById(R.id.radio_females);
            rb1.setChecked(true);

        }
    }
    //for dog sex filtering
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.all:
                if (checked)
                    sexPreference = "All";
                    break;
            case R.id.radio_females:
                if (checked)
                    sexPreference = "Female";
                    break;
            case R.id.radio_males:
                if(checked)
                    sexPreference = "Male";
                    break;

        }
    }

    void goToFilterActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
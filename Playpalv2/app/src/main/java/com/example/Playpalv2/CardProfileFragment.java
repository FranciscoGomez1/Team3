package com.example.Playpalv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.models.CardModel;
import com.example.Playpalv2.view_models.DogProfileViewModel;


public class CardProfileFragment extends Fragment {

    private TextView nameOfDog;
    private TextView dogAge;
    private TextView dogEnergy;
    private TextView dogWeight;
    private TextView dogAboutBio;

    private DogProfileViewModel dogOwnerProfile;
    private DogModel dog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_fragment_profile, container, false);
        setProfile(v);

        return v;
    }

    private void setProfile(View view) {
        dogOwnerProfile = new ViewModelProvider(requireActivity()).get(DogProfileViewModel.class);
        nameOfDog = view.findViewById(R.id.name_of_dog);
        dogAge = view.findViewById(R.id.dog_age);
        dogEnergy = view.findViewById(R.id.dog_energy);
        dogWeight = view.findViewById(R.id.dog_weight);
        dogAboutBio = view.findViewById(R.id.dog_about_bio);
        try {
            dogOwnerProfile.getDogProfile().observe(requireActivity(), DogOwnerProfile -> {
                dog = new DogModel();
                dog = dogOwnerProfile.getDogProfile().getValue();

                nameOfDog.setText("Name: " + dog.getName());
                dogAge.setText("Age: " + dog.getAge().toString());
                dogWeight.setText("Weight: " + dog.getWeight().toString());
                dogAboutBio.setText(dog.getBio());
            });
        }catch (Exception e){
            Log.e("Exception", e.getMessage());
        }
    }
}
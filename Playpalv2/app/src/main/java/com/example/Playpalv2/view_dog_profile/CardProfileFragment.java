package com.example.Playpalv2.view_dog_profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.Playpalv2.R;
import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.view_models.DogProfileViewModel;


public class CardProfileFragment extends Fragment {

    private TextView nameOfDog;
    private TextView dogAge;
    private TextView dogEnergy;
    private TextView dogWeight;
    private TextView dogAboutBio;

    private DogProfileViewModel dogProfile;
    private DogModel dog;

    ViewPager pager;
    ImagePagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_fragment_profile, container, false);
        setProfile(v);

        return v;
    }

    private void setProfile(View view) {
        dogProfile = new ViewModelProvider(requireActivity()).get(DogProfileViewModel.class);
        pager = view.findViewById(R.id.dog_viewpager);


        nameOfDog = view.findViewById(R.id.name_of_dog_owner);
        dogAge = view.findViewById(R.id.dog_age);
        dogEnergy = view.findViewById(R.id.dog_energy);
        dogWeight = view.findViewById(R.id.dog_weight);
        dogAboutBio = view.findViewById(R.id.dog_about_bio);


        try {
            dogProfile.getDogProfile().observe(requireActivity(), DogModel -> {
                dog = new DogModel();
                dog = dogProfile.getDogProfile().getValue();
                adapter = new ImagePagerAdapter(CardProfileFragment.this, dog.getImages());
                pager.setAdapter(adapter);

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
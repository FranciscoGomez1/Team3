package com.example.Playpalv2.view_dog_owner_profile;

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
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.view_models.DogOwnerView;


public class DogOwnerViewProfile extends Fragment {
    private TextView ownerName;
    private TextView ownerBio;

    private DogOwnerModel dogOwnerModel;
    private DogOwnerView dogOwnerView;

    ViewPager pager;
    ImageDogPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dog_owner_view_profile, container, false);
        setProfile(v);

        return v;
    }
    private void setProfile(View view){
        dogOwnerView = new ViewModelProvider(requireActivity()).get(DogOwnerView.class);
        pager = view.findViewById(R.id.dog_owner_viewpager);
        ownerName = view.findViewById(R.id.name_of_dog_owner);
        ownerBio = view.findViewById(R.id.dog_owner_about_bio);

        try{
            dogOwnerView.getTopDogLiveData().observe(requireActivity(), DogOwnerView ->{
                dogOwnerModel = new DogOwnerModel();
                dogOwnerModel =  dogOwnerView.getOwner().getValue();
                adapter = new ImageDogPagerAdapter(DogOwnerViewProfile.this, dogOwnerModel.getImages());
                pager.setAdapter(adapter);


               // adapter = new ImagePagerAdapter(DogOwnerViewProfile.this, dogOwnerModel.getImages());
                //pager.setAdapter(adapter);

                ownerName.setText(dogOwnerModel.getFirst_name() + " " +dogOwnerModel.getLast_name());
                ownerBio.setText(dogOwnerModel.getBio());
            });



        }catch (Exception e){
        Log.e("Exception", e.getMessage());
        }


    }
}
package com.example.Playpalv2.flipCards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.R;
import com.example.Playpalv2.models.CardModel;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.view_models.CardViewModel;
import com.example.Playpalv2.view_models.DogOwnerView;
import com.example.Playpalv2.view_models.DogViewModel;

public class CardBackFragment1 extends Fragment {
    private DogOwnerView ownerView;
    private TextView dogOwnerName;
    private TextView dogOwnerBio;
    private ImageView dogOwnerProfilePic;
    private String dogOwnerImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_back_1, container, false);
        setCard(v);
        return v;
    }

    private void setCard(View view) {
        ownerView = new ViewModelProvider(requireActivity()).get(DogOwnerView.class);
        dogOwnerProfilePic = view.findViewById(R.id.dog_owner_image1);
        dogOwnerName = view.findViewById(R.id.dog_owner_name1);
        dogOwnerBio = view.findViewById(R.id.dog_ower_bio1);
        ownerView.getOwner1().observe(requireActivity(), DogOwnerView-> {
            if(ownerView.getOwner1().getValue() != null) {

                dogOwnerName.setText(ownerView.getOwner1().getValue().getFirst_name());
                dogOwnerBio.setText(ownerView.getOwner1().getValue().getBio());
                dogOwnerImg = ownerView.getOwner1().getValue().getImages().get(0);
                // "https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/DogPhotesTest%2FPony.png?alt=media&token=a45c6b4b-1b42-4bf0-8db3-7baa45291771";

                try{
                    if (!dogOwnerImg.equals("")) {
                        Glide.with(this).load(dogOwnerImg).into(dogOwnerProfilePic);
                    }
                }catch(Exception e){

                    dogOwnerProfilePic.setImageResource(R.drawable.card_front);
                }

            }

        });


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (MainActivity.FragmentUtils.mDisableFragmentAnimations) {
            Animation a = new Animation() {};
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
package com.example.Playpalv2.flipCards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.R;

import java.util.Objects;

public class CardFrontFragment1 extends Fragment {
    DogViewModel dogViewModel;
    private TextView dogName;
    private TextView dogBio;
    private ImageView profilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_card_front_1, container, false);
        setCard(v);
        return v;
    }
    private void setCard(View view) {
        dogViewModel = new ViewModelProvider(requireActivity()).get(DogViewModel.class);

        dogViewModel.getDog1().observe(requireActivity(), DogViewModel -> {
            if(dogViewModel.getDog1().getValue() != null) {
                dogName = view.findViewById(R.id.dog_name);
                dogBio = view.findViewById(R.id.dog_bio);
                profilePic = view.findViewById(R.id.dog_front_image_1);


                dogName.setText(dogViewModel.getDog1().getValue().getName());
                dogBio.setText(dogViewModel.getDog1().getValue().getBio());

                String onlineImg = Objects.requireNonNull(dogViewModel.getDog1().getValue()).getAlbum().get(0);

                if(onlineImg != "") {
                    Glide.with(this).load(onlineImg).into(profilePic);
                }
                else{
                    profilePic.setImageResource(R.drawable.card_front);
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
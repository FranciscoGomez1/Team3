package com.example.Playpalv2.flipCards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.Playpalv2.R;

public class CardFrontFragment extends Fragment {
    DogViewModel dogViewModel;
    private TextView dogName;
    private TextView dogBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_front, container, false);
        setCard(v);
        return v;
    }

    private void setCard(View view) {
        dogViewModel = new ViewModelProvider(requireActivity()).get(DogViewModel.class);

        dogViewModel.getDog().observe(requireActivity(), DogViewModel -> {
            if(dogViewModel.getDog().getValue() != null) {
                dogName = view.findViewById(R.id.dog_name);
                dogBio = view.findViewById(R.id.dog_bio);

                dogName.setText(dogViewModel.getDog().getValue().getName());
                dogBio.setText(dogViewModel.getDog().getValue().getBio());
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

package com.example.Playpalv2.flipCards;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.util.Log;
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

import java.net.URI;
import java.net.URL;


public class CardFrontFragment extends Fragment {
    DogViewModel dogViewModel;
    private TextView dogName;
    private TextView dogBio;
    private ImageView profilePic;
    private String onlineImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_front, container, false);
        setCard(v);
        return v;
    }

    private void setCard(View view) {
        dogViewModel = new ViewModelProvider(requireActivity()).get(DogViewModel.class);
        dogName = view.findViewById(R.id.dog_name);
        dogBio = view.findViewById(R.id.dog_bio);
        profilePic = view.findViewById(R.id.dog_front_image);

        dogViewModel.getDog().observe(requireActivity(), DogViewModel -> {
            if(dogViewModel.getDog().getValue() != null) {


                dogName.setText(dogViewModel.getDog().getValue().getName());
                dogBio.setText(dogViewModel.getDog().getValue().getBio() );
                onlineImg = dogViewModel.getDog().getValue().getAlbum().get(0);
                        //"https://firebasestorage.googleapis.com/v0/b/playpalv2-9e341.appspot.com/o/DogPhotesTest%2FPony.png?alt=media&token=a45c6b4b-1b42-4bf0-8db3-7baa45291771";

                if(onlineImg != "") {
                    Glide.with(this).load(onlineImg).into(profilePic);
                } else{
                    profilePic.setImageResource(R.drawable.card_front);
                }


            }

        });

       /* profilePic.setOnClickListener(View ->{
            Log.i("DOGGO IMAGE", "I CLICKED THE IMAGE OF THE DOG!!!!!!!");
        });
*/
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

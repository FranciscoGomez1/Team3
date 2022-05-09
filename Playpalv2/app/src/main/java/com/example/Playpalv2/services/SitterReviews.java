package com.example.Playpalv2.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.R;
import com.example.Playpalv2.adapters.ReviewsAdapter;
import com.example.Playpalv2.get_from_firestore.GetReviews;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.models.MessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class SitterReviews extends AppCompatActivity {

    private ImageView backBtn;
    private ImageView sitterProfilePic;
    private TextView sitterName;

    private DogOwnerModel owner;

    private FirebaseFirestore db;
    private RecyclerView adapterList;
    private FirestoreRecyclerAdapter adapterChat;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private GetReviews getReviews;
    private RecyclerView recyclerView;
    private String sittingReivewsCollection = "sittingReviews";

    private List<MessageModel> messages;
    private EditText inputMessage;

    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sitter_reviews);
        Intent intent = getIntent();
        owner = (DogOwnerModel) intent.getSerializableExtra("ServiceProvider");
        getReviews = new GetReviews(owner.getId(), sittingReivewsCollection);


        backBtn = findViewById(R.id.backBtn);
        sitterProfilePic = findViewById(R.id.profileImg);
        sitterName = findViewById(R.id.walker_name);
        sitterName = findViewById(R.id.walker_name);
        sitterName.setText(owner.getFirst_name() + " " + owner.getLast_name());
        Glide.with(this)
                .load(owner.getImages().get(1))
                .into(sitterProfilePic);

        db = FirebaseFirestore.getInstance();   //
        adapterList = findViewById(R.id.chatRecyclerView);


        getReviews.fetchReviews(reviews ->{
            Log.e("WALKING REVIEWS", reviews.get(0).getReview());
            recyclerView = findViewById(R.id.sitter_reviews_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            reviewsAdapter =  new ReviewsAdapter(this, reviews);
            recyclerView.setAdapter(reviewsAdapter);


        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SitterReviews.this, Services.class);
                startActivity(i);
            }
        });
    }



}


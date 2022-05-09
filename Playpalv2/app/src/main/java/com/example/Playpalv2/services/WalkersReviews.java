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
import com.example.Playpalv2.ChatRoom;
import com.example.Playpalv2.R;
import com.example.Playpalv2.adapters.ReviewsAdapter;
import com.example.Playpalv2.firestore_updates.RecordUserChoice;
import com.example.Playpalv2.get_from_firestore.GetReviews;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.models.MessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WalkersReviews extends AppCompatActivity {
    private ImageView backBtn;
    private ImageView sitterProfilePic;
    private TextView walkerName;

    private DogOwnerModel owner;

    private FirebaseFirestore db;
    private RecyclerView adapterList;
    private FirestoreRecyclerAdapter adapterChat;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private GetReviews getReviews;
    private RecyclerView recyclerView;
    private String walkingReivewsCollection = "walkingReviews";

    private List<MessageModel> messages;
    private EditText inputMessage;

    private ReviewsAdapter reviewsAdapter;

    private MaterialButton contactWalker;

    private RecordUserChoice recordUserChoice = new RecordUserChoice();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_walkers_reviews);
        Intent intent = getIntent();
        owner = (DogOwnerModel) intent.getSerializableExtra("walker");
        getReviews = new GetReviews(owner.getId(), walkingReivewsCollection);

        backBtn = findViewById(R.id.backBtn);
        sitterProfilePic = findViewById(R.id.profileImg);
        walkerName = findViewById(R.id.walker_name);
        walkerName.setText(owner.getFirst_name() + " " + owner.getLast_name());
        Glide.with(this)
                .load(owner.getImages().get(1))
                .into(sitterProfilePic);

        db = FirebaseFirestore.getInstance();   //
        String userId = owner.getId();
        contactWalker = findViewById(R.id.btn_contact_walker_provider);

        contactWalker.setOnClickListener(View ->
        {
            recordUserChoice.recordTheMatch(currentUser, owner.getId());
            Intent intent2 = new Intent(WalkersReviews.this, ChatRoom.class);
            intent2.putExtra("dogOwner", owner);
            intent2.putExtra("comesBackToWalkersReview", true);
            intent2.putExtra("comesBackToSittersReview", false);
            intent2.putExtra("walker", owner);
            startActivity(intent2);
            Log.e("does it work", "Click");

        });


        getReviews.fetchReviews(reviews ->{
            Log.e("WALKING REVIEWS", reviews.get(0).getReview());
            recyclerView = findViewById(R.id.walker_reviews_recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            reviewsAdapter =  new ReviewsAdapter(this, reviews);
            recyclerView.setAdapter(reviewsAdapter);


        });





        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WalkersReviews.this, Services.class);
                startActivity(i);
            }
        });
    }
}
package com.example.Playpalv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.models.MessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
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

    private List<MessageModel> messages;
    private EditText inputMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkers_reviews);
        Intent intent = getIntent();
        owner = (DogOwnerModel) intent.getSerializableExtra("walker");


        backBtn = findViewById(R.id.backBtn);
        sitterProfilePic = findViewById(R.id.profileImg);
        walkerName = findViewById(R.id.walker_name);
        walkerName.setText(owner.getFirst_name() + " " + owner.getLast_name());
        Glide.with(this)
                .load(owner.getImages().get(1))
                .into(sitterProfilePic);

        db = FirebaseFirestore.getInstance();   //
        adapterList = findViewById(R.id.chatRecyclerView);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WalkersReviews.this, Services.class);
                startActivity(i);
            }
        });
    }
}
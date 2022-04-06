package com.example.Playpalv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.databinding.ActivityMessagesBinding;
import com.example.Playpalv2.flipCards.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Messages extends DrawerBase {
    ActivityMessagesBinding activityMessagesBinding; //For services

    private FirebaseFirestore db;   //
    private RecyclerView mFirestoreList;
    private RecyclerView mFirestoreList2; //CHANGE
    private FirestoreRecyclerAdapter adapter;
    private FirestoreRecyclerAdapter adapter2;    //CHANGE

    private BottomNavigationView bottomNavigationView;//FOR NAVIGATION BAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        activityMessagesBinding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(activityMessagesBinding.getRoot());

        db = FirebaseFirestore.getInstance();   //
        mFirestoreList = findViewById(R.id.newRecyclerView);
        mFirestoreList2 = findViewById(R.id.newMatchesRecyclerView);

/*        for(int i = 0; i < 15; i++){
            newMatches.add(
                    new NewMatches(
                            "Rocky ".concat(String.valueOf(i)),
                            getResources().getString(R.string.dummy_text)
                    )
            );
        }

        newRecyclerView.setAdapter(new NewMatchesAdapter(newMatches));*/

/*
        //RecyclerView for NewChat
        RecyclerView newMatchesRecyclerView = findViewById(R.id.newMatchesRecyclerView);
        List<NewChat> newChats = new ArrayList<>();

        //ImageView myImageView = (ImageView) findViewById(R.id.image);

        for(int i = 0; i < 15; i++){
            newChats.add(
                    new NewChat(
                            "Benny ".concat(String.valueOf(i)),
                            getResources().getString(R.string.dummy_text)
                    )
            );
        }

        newMatchesRecyclerView.setAdapter(new NewChatAdapter(newChats));

*/

        //FOR NAVIGATION BAR
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.messages);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.messages) {
                return true;
            } else if (itemId == R.id.services) {
                startActivity(new Intent(getApplicationContext(), Services.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
        setUpRecyclerView();
        setUpRecyclerView1();
    }

    private void setUpRecyclerView() {
        Query query = db.collection("Test");

        FirestoreRecyclerOptions<NewMatches> options = new FirestoreRecyclerOptions.Builder<NewMatches>()
                .setQuery(query, NewMatches.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<NewMatches, NewMatchesHolder>(options) {
            @NonNull
            @Override
            public NewMatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_container, parent, false);
                return new NewMatchesHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NewMatchesHolder holder, int position, @NonNull NewMatches model) {
                holder.firstName.setText(model.getFirstName());
                holder.bio.setText(model.getBio());
                //holder.time.setText(model.getTime());
                //Picasso.get().load(model.getImage()).into(holder.images);
                Glide.with(holder.itemView.getContext())
                        .load(model.getImage())
                        .into(holder.images);
            }
        };

        //mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class NewMatchesHolder extends RecyclerView.ViewHolder {

        private TextView firstName;
        private TextView bio;
        private CircleImageView images;
        //private TextView time;

        public NewMatchesHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            bio = itemView.findViewById(R.id.bio);
            //images = (CircleImageView) findViewById(R.id.image);
            images = (CircleImageView) itemView.findViewById(R.id.images);
            //time = itemView.findViewById(R.id.time);

/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAbsoluteAdapterPosition());

                }
            });
*/

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(View v, getAbsoluteAdapterPosition());
                    }
                }
            });*/

        }

        /*private NewMatchesHolder.ClickListener mClickListener;

        public interface ClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(NewMatchesHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }*/
    }

    private void setUpRecyclerView1() {
        Query query = db.collection("Test");

        FirestoreRecyclerOptions<NewChat> options = new FirestoreRecyclerOptions.Builder<NewChat>()
                .setQuery(query, NewChat.class)
                .build();

            adapter2 = new FirestoreRecyclerAdapter<NewChat, NewChatHolder>(options) {
            @NonNull
            @Override
            public NewChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_horizontal_container, parent, false);
                return new NewChatHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NewChatHolder holder, int position, @NonNull NewChat model) {
                holder.firstName.setText(model.getFirstName());
            }
        };

        //mFirestoreList2.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList2.setAdapter(adapter2);
    }

    private class NewChatHolder extends RecyclerView.ViewHolder{

        private TextView firstName;
        //private ImageView images;
        //private TextView time;

        public NewChatHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            //images = itemView.findViewById(R.id.bio);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }

}
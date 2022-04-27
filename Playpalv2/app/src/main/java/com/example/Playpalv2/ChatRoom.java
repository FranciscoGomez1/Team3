package com.example.Playpalv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Query;
import com.google.firestore.v1.WriteResult;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoom extends AppCompatActivity {

    private EditText newMsg;
    private ImageView sendBtn;
    private ImageView backBtn;
    private ImageView dogPic;
    private TextView firstName;

    private FirebaseFirestore db;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String firebaseUser = auth.getCurrentUser().getUid();
    private RecyclerView adapterList;
    private FirestoreRecyclerAdapter adapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        Intent intent = getIntent();
        String dogName = intent.getStringExtra("dogName");
        String dogImage = intent.getStringExtra("dogImage");
        Log.e("PLEASE GOD", dogName);
        Log.e("PLEASE GOD", dogImage);

        newMsg = findViewById(R.id.newMsg);
        sendBtn = findViewById(R.id.sendMsg);
        backBtn = findViewById(R.id.backBtn);
        dogPic = findViewById(R.id.profileImg);
        firstName = findViewById(R.id.firstName);

        firstName.setText(dogName);
        Glide.with(this)
                .load(dogImage)
                .into(dogPic);

        db = FirebaseFirestore.getInstance();   //
        adapterList = findViewById(R.id.chatRecyclerView);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatRoom.this, Messages.class);
                startActivity(i);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMsg = newMsg.getText().toString();
                //Toast.makeText(ChatRoom.this, currentMsg, Toast.LENGTH_SHORT).show();
                //get current timestamps
                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 13);

                //create unique key for db that will contain msg, timestamp, and user's id in db
                //String key = db.getInstance().collection("users").document("chats").collection("chatId").add().getKey();

                if (currentMsg.length() != 0) {
                    CollectionReference msgHistory = db.collection("Chat Rooms")
                            .document()
                            .collection("Messages");

                    Map<String, Object> msgContent = new HashMap<>();

                    msgContent.put("msg", currentMsg);
                    msgContent.put("timestamp", currentTimeStamp);
                    msgContent.put("sender", firebaseUser);
                    msgHistory.add(msgContent);

                    newMsg.setText("");


                }
            }
        });

        //setUpRecyclerView5();
    }

    /*private void setUpRecyclerView5() {
        Query query = db.collection("Chat Rooms").document().collection("Messages");

        FirestoreRecyclerOptions<ChatRoomModel> options = new FirestoreRecyclerOptions.Builder<ChatRoomModel>()
                .setQuery(query, ChatRoomModel.class)
                .build();

        adapterChat = new FirestoreRecyclerAdapter<ChatRoomModel, ChatRoom.ChatRoomModelHolder>(options) {
            @NonNull
            @Override
            public ChatRoom.ChatRoomModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, parent, false);
                return new ChatRoom.ChatRoomModelHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatRoom.ChatRoomModelHolder holder, int position, @NonNull ChatRoomModel model) {

                //ChatList list2 = chatLists.get(position);

                *//*if(list2.getSenderId().equals(userId)){
                    holder.myLayout.setVisibility(View.VISIBLE);
                    holder.oppoLayout.setVisibility(View.GONE);

                    holder.myMSG.setText(list2.getMessage());
                    holder.myTime.setText(list2.getDate()+" "+list2.getTime());
                }else{
                    holder.myLayout.setVisibility(View.GONE);
                    holder.oppoLayout.setVisibility(View.VISIBLE);

                    holder.oppoMSG.setText(list2.getMessage());
                    holder.oppoTime.setText(list2.getDate()+" "+list2.getTime());
                }*//*


                holder.oppoMSG.setText(model.getOppoMSG());
                holder.myMSG.setText(model.getMyMSG());
            }
        };

        //mFirestoreList.setHasFixedSize(true);
        adapterList.setLayoutManager(new LinearLayoutManager(this));
        adapterList.setAdapter(adapterChat);

    }

    private class ChatRoomModelHolder extends RecyclerView.ViewHolder {

        private TextView oppoMSG;
        private TextView myMSG;

        private LinearLayout oppoLayout;
        private LinearLayout myLayout;
        Context context;
        //private TextView time;

        public ChatRoomModelHolder(@NonNull View itemView) {
            super(itemView);

            oppoMSG = itemView.findViewById(R.id.oppoMSG);
            myMSG = itemView.findViewById(R.id.myMSG);
            //images = (CircleImageView) findViewById(R.id.image);
            //images = (CircleImageView) itemView.findViewById(R.id.images);
            //time = itemView.findViewById(R.id.time);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterChat.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapterChat.stopListening();
    }*/
}

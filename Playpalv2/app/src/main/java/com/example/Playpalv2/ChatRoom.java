package com.example.Playpalv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoom extends AppCompatActivity {

    private EditText newMsg;
    private ImageView sendBtn;
    private ImageView backBtn;
    private ImageView dogPic;
    private TextView firstName;

    private FirebaseFirestore db;
    private RecyclerView adapterList;
    private FirestoreRecyclerAdapter adapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        newMsg = findViewById(R.id.newMsg);
        sendBtn = findViewById(R.id.sendMsg);
        backBtn = findViewById(R.id.backBtn);
        dogPic = findViewById(R.id.profileImg);
        firstName = findViewById(R.id.firstName);

        //Intent intent = getIntent();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatRoom.this, Messages.class);
                startActivity(i);
            }
        });

        /*sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentMsg = newMsg.getText().toString();
                //Toast.makeText(ChatRoom.this, currentMsg, Toast.LENGTH_SHORT).show();
                //get current timestamps
                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0,13);

                //create unique key for db that will contain msg, timestamp, and user's id in db
                String key = FirebaseDatabase.getInstance().getReference("users").child("chats").child(chatId).push().getKey();

                if (currentMsg.length() != 0) {
                    DatabaseReference msgHistory = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("history").child(key);

                    Map<String, Object> msgContent = new HashMap<>();

                    msgContent.put("msg", currentMsg);
                    msgContent.put("timestamp", currentTimeStamp);
                    msgContent.put("sender", userId);
                    msgHistory.updateChildren(msgContent);

                    newMsg.setText("");
                }
            }
        });*/
    }

}


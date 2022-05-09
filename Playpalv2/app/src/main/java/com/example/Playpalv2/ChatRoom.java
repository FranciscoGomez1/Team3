package com.example.Playpalv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.adapters.ChatAdapter;
import com.example.Playpalv2.databinding.ChatRoomBinding;
import com.example.Playpalv2.matches.Messages;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.models.MessageModel;
import com.example.Playpalv2.services.SitterReviews;
import com.example.Playpalv2.services.WalkersReviews;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class ChatRoom extends AppCompatActivity {

    private EditText newMsg;
    private ImageView sendBtn;
    private ImageView backBtn;
    private ImageView dogPic;
    private TextView firstName;

    private DogOwnerModel owner;

    private FirebaseFirestore db;
    private RecyclerView adapterList;
    private FirestoreRecyclerAdapter adapterChat;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private List<MessageModel> messages;
    private ChatAdapter chatAdapter;
    private EditText inputMessage;
    private ChatRoomBinding binding;
    private Boolean goesBackToSittersReviews;
    private Boolean goesToWalkersReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        owner = (DogOwnerModel) intent.getSerializableExtra("dogOwner");
        goesToWalkersReviews = (Boolean) intent.getBooleanExtra("comesBackToWalkersReview", false);
        goesBackToSittersReviews = (Boolean) intent.getBooleanExtra("comesBackToSittersReview", false);

        inputMessage = findViewById(R.id.newMsg);
       // String dogName = intent.getStringExtra("dogName");
        //String dogImage = intent.getStringExtra("dogImage");
        Log.e("PLEASE GOD", owner.getFirst_name());
        //Log.e("PLEASE GOD", dogImage);

        newMsg = findViewById(R.id.newMsg);
        sendBtn = findViewById(R.id.sendMsg);
        backBtn = findViewById(R.id.backBtn);
        dogPic = findViewById(R.id.profileImg);
        firstName = findViewById(R.id.firstName);
        firstName.setText(owner.getFirst_name());

        Glide.with(this)
                .load(owner.getImages().get(1))
                .into(dogPic);

        db = FirebaseFirestore.getInstance();   //
        adapterList = findViewById(R.id.chatRecyclerView);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(goesBackToSittersReviews){
                    i = new Intent(ChatRoom.this, SitterReviews.class);
                    i.putExtra("ServiceProvider", owner);
                } else if(goesToWalkersReviews){
                    i = new Intent(ChatRoom.this, WalkersReviews.class);
                    i.putExtra("walker", owner);
                }else {
                    i = new Intent(ChatRoom.this, Messages.class);

                }
                startActivity(i);
            }
        });

        sendBtn.setOnClickListener(View -> {
            sendMessage();
            inputMessage.setText("");
        });
/*
        private void sendMessage(String sender, String receiver, String message) {
            DatabaseReference reference = FirebaseFirestore.getInstance();
            db = FirebaseFirestore.getInstance().getClass();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("receiver", receiver);
            hashMap.put("message", message);
        });*/

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
        //setUpRecyclerView5();
        init();
        listenMessages();
    }

    private void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put("SenderID", currentUser);
        message.put("ReceiverID", owner.getId());
        message.put("Message", inputMessage.getText().toString());
        message.put("TimeStamp", new Date());
        db.collection("Dog_Matches").document(getMatchDocumentId(currentUser,owner.getId())).collection("Messages").add(message);
    }

    private void init(){
        //preferenceManager = new PreferenceManager(getApplicationContext());
        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                messages, owner.getId(), owner.getImages().get(1)

        );
        binding.chatRecyclerView.setAdapter(chatAdapter);

    }

    private void listenMessages(){
        db.collection("Dog_Matches").document(getMatchDocumentId(currentUser,owner.getId()))
                .collection("Messages").addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            int count = messages.size();

            for(DocumentChange documentChange: value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED) {
                    MessageModel message = new MessageModel();
                    message.senderId = documentChange.getDocument().getString("ReceiverID");
                    message.receiverId = documentChange.getDocument().getString("ReceiverID");
                    message.message = documentChange.getDocument().getString("Message");
                    message.dateTime = getDataToString(documentChange.getDocument().getDate("TimeStamp"));
                    message.dateOfMessage = documentChange.getDocument().getDate("TimeStamp");
                    messages.add(message);
                }
            }
            Collections.sort(messages, Comparator.comparing(obj -> obj.dateOfMessage));
            if(count == 0){
                chatAdapter.notifyDataSetChanged();
            }else{
                Log.e("MESSAGES", messages.get(1).message);
                chatAdapter.notifyItemRangeInserted(messages.size(),
                        messages.size());
                binding.chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
            }
        }
    };

    private Bitmap getBitmapFromEnCodedString(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String getMatchDocumentId(String userId, String ownerId) {
        int comparedResult = userId.compareTo(ownerId);

        if (comparedResult > 0) {
            return userId + ownerId;
        } else if (comparedResult < 0) {
            return ownerId + userId;
        }
        return null;
    }

    private String getDataToString(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

   /* private void setUpRecyclerView5() {
        Query query = db.collection("Test Msg");

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

                if(list2.getSenderId().equals(userId)){
                    holder.myLayout.setVisibility(View.VISIBLE);
                    holder.oppoLayout.setVisibility(View.GONE);

                    holder.myMSG.setText(list2.getMessage());
                    holder.myTime.setText(list2.getDate()+" "+list2.getTime());
                }else{
                    holder.myLayout.setVisibility(View.GONE);
                    holder.oppoLayout.setVisibility(View.VISIBLE);

                    holder.oppoMSG.setText(list2.getMessage());
                    holder.oppoTime.setText(list2.getDate()+" "+list2.getTime());
                }


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


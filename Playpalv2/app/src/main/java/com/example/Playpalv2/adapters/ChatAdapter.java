package com.example.Playpalv2.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Playpalv2.databinding.MessageReceivedContainerBinding;
import com.example.Playpalv2.databinding.MessageSentContainerBinding;
import com.example.Playpalv2.models.MessageModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<MessageModel> messages;
    private final String senderId;
    private final String receiverImage;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;


    public ChatAdapter(List<MessageModel> messages, String senderId, String receiverImage) {
        this.messages = messages;
        this.senderId = senderId;
        this.receiverImage = receiverImage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    MessageSentContainerBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,false
                    )
            );
        }else{
            return new ReceivedMessageViewHolder(
                    MessageReceivedContainerBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(messages.get(position));
        }else{
            ((ReceivedMessageViewHolder) holder).setData(messages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position){
        if(messages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        } else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final MessageSentContainerBinding binding;
        ImageView imageView;
        SentMessageViewHolder(MessageSentContainerBinding messageSentContainerBinding){
            super(messageSentContainerBinding.getRoot());
            binding = messageSentContainerBinding;
        }
        void setData(MessageModel messageModel){
            binding.textMessage.setText(messageModel.message);
            binding.textDateTime.setText(messageModel.dateTime);
        }
    }

    static  class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
       private final MessageReceivedContainerBinding  binding;

       ReceivedMessageViewHolder(MessageReceivedContainerBinding messageReceivedContainerBinding){
           super(messageReceivedContainerBinding.getRoot());
           binding = messageReceivedContainerBinding;
       }

       void setData(MessageModel messageModel){
          binding.textMessage.setText(messageModel.message);
          binding.textDateTime.setText(messageModel.dateTime);

       }


    }
}

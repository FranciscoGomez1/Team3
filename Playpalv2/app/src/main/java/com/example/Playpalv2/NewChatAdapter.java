package com.example.Playpalv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewChatAdapter extends RecyclerView.Adapter<NewChatAdapter.NewChatViewHolder> {

    private List<NewChat> newChats;

    NewChatAdapter(List<NewChat> newChats) { this.newChats = newChats; }

    @NonNull
    @Override
    public NewChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewChatViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.match_horizontal_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewChatViewHolder holder, int position) {
        holder.textTitle.setText(newChats.get(position).getFirstName());
        //holder.textDescription.setText(newChats.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return newChats.size();
    }

    class NewChatViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle, textDescription;

        NewChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.firstName);
            //image = itemView.findViewById(R.id.image);
            //textDescription = itemView.findViewById(R.id.textDescription);
        }
    }
}

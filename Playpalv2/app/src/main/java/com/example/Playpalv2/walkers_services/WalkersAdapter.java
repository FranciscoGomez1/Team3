package com.example.Playpalv2.walkers_services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.ChatRoom;
import com.example.Playpalv2.R;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.servicesWalking;

import java.util.LinkedList;
import java.util.List;


public class WalkersAdapter extends RecyclerView.Adapter<WalkersAdapter.WalkerViewHolder> {

    private final LinkedList<DogOwnerModel> matches;
    private Context context;
    public WalkersAdapter( Context context,LinkedList<DogOwnerModel> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public WalkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_providers_container, parent, false);
       return new WalkerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkerViewHolder holder, int position) {
        Glide.with(context).load(matches.get(position).getImages().get(1)).into(holder.imageView);

        holder.first_name.setText(matches.get(position).getFirst_name()
                + " " + matches.get(position).getLast_name()
            );

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    class WalkerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView first_name;
        WalkerViewHolder(@NonNull View matchContainerView){
            super(matchContainerView);
            first_name = matchContainerView.findViewById(R.id.first_name);
            imageView = matchContainerView.findViewById(R.id.image);

            matchContainerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), ChatRoom.class);
                    i.putExtra("dogOwner", matches.get(getAbsoluteAdapterPosition()));
                    view.getContext().startActivity(i);
                }
            });


        }

    }
}

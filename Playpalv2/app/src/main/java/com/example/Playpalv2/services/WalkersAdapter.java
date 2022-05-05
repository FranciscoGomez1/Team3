package com.example.Playpalv2.services;

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

import java.util.List;

public class WalkersAdapter extends RecyclerView.Adapter<WalkersAdapter.WalkersViewHolder>{
    private final List<DogOwnerModel> matches;
    private Context context;
    public WalkersAdapter(Context context, List<DogOwnerModel> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public WalkersAdapter.WalkersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.services_providers_container, parent, false);
        return new WalkersAdapter.WalkersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkersAdapter.WalkersViewHolder holder, int position) {
        Glide.with(context).load(matches.get(position).getImages().get(1)).into(holder.imageView);

        holder.name.setText(matches.get(position).getFirst_name()
                + " " + matches.get(position).getLast_name()
        );

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    class WalkersViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        public WalkersViewHolder(@NonNull View serviceProvidersContainer){
            super(serviceProvidersContainer);
            name = serviceProvidersContainer.findViewById(R.id.service_provider_type);
            imageView = serviceProvidersContainer.findViewById(R.id.image);

            serviceProvidersContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), ChatRoom.class);
                    i.putExtra("service_provider", matches.get(getAbsoluteAdapterPosition()));
                    view.getContext().startActivity(i);
                }

            });

        }
        public List getMatches(){
            return matches;
        }
    }

}

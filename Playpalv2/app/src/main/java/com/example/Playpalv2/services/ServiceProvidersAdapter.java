package com.example.Playpalv2.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Playpalv2.R;
import com.example.Playpalv2.models.DogOwnerModel;

import java.util.List;

public class ServiceProvidersAdapter extends RecyclerView.Adapter<ServiceProvidersAdapter.ServiceProviderViewHolder>{
    private final List<DogOwnerModel> matches;
    private Context context;
    public ServiceProvidersAdapter(Context context, List<DogOwnerModel> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public ServiceProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.services_providers_container, parent, false);
        return new ServiceProviderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderViewHolder holder, int position) {
        Glide.with(context).load(matches.get(position).getImages().get(1)).into(holder.imageView);

        holder.name.setText(matches.get(position).getFirst_name()
                + " " + matches.get(position).getLast_name()
        );

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    class ServiceProviderViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        public ServiceProviderViewHolder(@NonNull View serviceProvidersContainer){
            super(serviceProvidersContainer);
            name = serviceProvidersContainer.findViewById(R.id.service_provider_type);
            imageView = serviceProvidersContainer.findViewById(R.id.image);

            serviceProvidersContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("RECYLER ITME", "GOT CLICK");
                    Intent i = new Intent(view.getContext(), SitterReviews.class);
                    i.putExtra("ServiceProvider", matches.get(getAbsoluteAdapterPosition()));
                    view.getContext().startActivity(i);
                }

            });

        }
        public List getMatches(){
            return matches;
        }
    }

}

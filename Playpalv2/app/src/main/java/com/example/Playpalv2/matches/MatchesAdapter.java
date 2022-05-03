package com.example.Playpalv2.matches;

import static android.util.Base64.decode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
import com.example.Playpalv2.databinding.MatchContainerBinding;
import com.example.Playpalv2.models.DogOwnerModel;

import java.util.List;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatcheeViewHolder> {

    private final List<DogOwnerModel> matches;
    private Context context;
    public MatchesAdapter(Context context, List<DogOwnerModel> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatcheeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.match_container, parent, false);
       return new MatcheeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatcheeViewHolder holder, int position) {
        Glide.with(context).load(matches.get(position).getImages().get(1)).into(holder.imageView);

        holder.fname.setText(matches.get(position).getFirst_name());

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    class MatcheeViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView fname;
        MatcheeViewHolder(@NonNull View matchContainerView){
            super(matchContainerView);
            fname = matchContainerView.findViewById(R.id.firstName);
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

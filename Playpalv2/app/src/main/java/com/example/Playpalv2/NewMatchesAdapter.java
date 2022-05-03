package com.example.Playpalv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewMatchesAdapter extends RecyclerView.Adapter<NewMatchesAdapter.NewMatchesViewHolder> {

    private List<NewMatches> newMatches;

    NewMatchesAdapter(List<NewMatches> newMatches) {
        this.newMatches = newMatches;
    }

    @NonNull
    @Override
    public NewMatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewMatchesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.match_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewMatchesViewHolder holder, int position) {
        holder.textTitle.setText(newMatches.get(position).getFirstName());
        holder.textDescription.setText(newMatches.get(position).getBio());
    }

    @Override
    public int getItemCount() {
        return newMatches.size();
    }

    class NewMatchesViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle, textDescription;

        NewMatchesViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.firstName);
            textDescription = itemView.findViewById(R.id.dog_name);
        }
    }
}
package com.example.Playpalv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Playpalv2.R;
import com.example.Playpalv2.models.ReviewModel;

import java.util.LinkedList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private Context context;
    private LinkedList<ReviewModel> reviews;

    public ReviewsAdapter(Context context, LinkedList<ReviewModel> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.reviews_container, parent, false);


        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewHolder holder, int position) {

        ReviewModel review = reviews.get(position);
        holder.reviewerName.setText("Review By: " + review.getReviewer());
        holder.reviewContent.setText(review.getReview());
        holder.rating.setText("Rating given: "  + review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView reviewerName;
        TextView reviewContent;
        TextView rating;


        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewContent= itemView.findViewById(R.id.review);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}

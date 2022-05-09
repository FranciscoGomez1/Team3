package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.models.ReviewModel;

import java.util.List;

public interface OnGotReviewsListener {
    void onGotReviews(List<ReviewModel> reviews);
}

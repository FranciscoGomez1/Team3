package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.models.ReviewModel;

import java.util.LinkedList;

public interface OnGotReviewsListener {
    void onGotReviews(LinkedList<ReviewModel> reviews);
}

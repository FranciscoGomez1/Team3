package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.Playpalv2.models.ReviewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.Objects;

public class GetReviews {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ReviewModel review = new ReviewModel();
    private LinkedList<ReviewModel> reviews = new LinkedList<>();

    private OnGotReviewsListener onGotReviewsListener;
    private String mAuth = FirebaseAuth.getInstance().getUid();

    private String walkingReviewsCollection;
    private String reviewee;

    private CollectionReference collecRef;


    private int sizeOfQuerry = 0;
    private int reviewsChecked = 0;

    public GetReviews(String reviewee, String walkingReviewsCollection) {
        this.walkingReviewsCollection = walkingReviewsCollection;
        this.reviewee = reviewee;
    }

    public void fetchReviews(OnGotReviewsListener onGotReviewsListener){
        getReviews(reviewee, walkingReviewsCollection, onGotReviewsListener);
    }

    private void getReviews(String reviewee, String walkingReviewsCollection, OnGotReviewsListener gotReviewsListener ){

        db.collection("Dog Owners").document(reviewee).collection(walkingReviewsCollection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                sizeOfQuerry = queryDocumentSnapshots.size();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    review = Objects.requireNonNull(documentSnapshot.toObject(ReviewModel.class));
                    reviews.add(review);
                    reviewsChecked++;
                    if(reviewsChecked == sizeOfQuerry){
                        gotReviewsListener.onGotReviews(reviews);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("What HAppen", e.getMessage());
            }
        });

    }


}

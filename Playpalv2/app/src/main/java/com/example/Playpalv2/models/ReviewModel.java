package com.example.Playpalv2.models;

public class ReviewModel {
    String reviewer;
    String review;

    public ReviewModel() {
    }

    public ReviewModel(String reviewer, String review) {
        this.reviewer = reviewer;
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

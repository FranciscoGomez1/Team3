package com.example.Playpalv2.models;

public class ReviewModel {
    String reviewer;
    String review;
    Integer rating;
    public ReviewModel() {
    }

    public ReviewModel(String reviewer, String review, Integer rating) {
        this.reviewer = reviewer;
        this.review = review;
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

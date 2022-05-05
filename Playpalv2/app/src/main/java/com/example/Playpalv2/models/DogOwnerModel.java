package com.example.Playpalv2.models;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;
@Keep
public class DogOwnerModel implements Serializable {
    private String account_created;
    private String bio;
    private String dog_id;
    private String dog_breed;
    private String dogs_disLiked;
    private String dogs_liked;
    private String dogs_seen;
    private String email;
    private String first_name;
    private String has_sitter;
    private String has_walker;
    private String id;
    private List<String> images;
    private String last_name;
    private String location;
    private String mobile_number;
    private Boolean isSitter;



    public DogOwnerModel() {
    }

    public DogOwnerModel(String account_created, String bio, String dog_id, String dog_breed, Boolean
            isSitter,  String dogs_disliked, String dogs_liked, String dogs_seen, String
            email, String first_name, String has_sitter, String has_walker,
            String id, List<String> images, String last_name, String location,
            String mobile_number) {
        this.account_created = account_created;
        this.bio = bio;
        this.dog_id = dog_id;
        this.dog_breed = dog_breed;
        this.isSitter = isSitter;
        this.dogs_disLiked = dogs_disliked;
        this.dogs_liked = dogs_liked;
        this.dogs_seen = dogs_seen;
        this.email = email;
        this.first_name = first_name;
        this.has_sitter = has_sitter;
        this.has_walker = has_walker;
        this.id = id;
        this.images = images;
        this.last_name = last_name;
        this.location = location;
        this.mobile_number = mobile_number;
    }

    public String getAccount_created() {
        return account_created;
    }

    public void setAccount_created(String account_created) {
        this.account_created = account_created;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDog_id() {
        return dog_id;
    }

    public void setDog_id(String dog_id) {
        this.dog_id = dog_id;
    }

    public String getDog_breed() {
        return dog_breed;
    }

    public void setDog_breed(String dog_breed) {
        this.dog_breed = dog_breed;
    }

    public String getDogs_disLiked() {
        return dogs_disLiked;
    }

    public void setDogs_disLiked(String dogs_disliked) {
        this.dogs_disLiked = dogs_disliked;
    }

    public String getDogs_liked() {
        return dogs_liked;
    }

    public void setDogs_liked(String dogs_liked) {
        this.dogs_liked = dogs_liked;
    }

    public String getDogs_seen() {
        return dogs_seen;
    }

    public void setDogs_seen(String dogs_seen) {
        this.dogs_seen = dogs_seen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getHas_sitter() {
        return has_sitter;
    }

    public void setHas_sitter(String has_sitter) {
        this.has_sitter = has_sitter;
    }

    public String getHas_walker() {
        return has_walker;
    }

    public void setHas_walker(String has_walker) {
        this.has_walker = has_walker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
    public Boolean getSitter() {
        return isSitter;
    }
    public void setSitter(Boolean sitter) {
        isSitter = sitter;
    }
}



package com.example.Playpalv2;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class NewMatches {
    private String firstName, bio, images; //time
    //private Date mTimestamp;

    public NewMatches() {}

    public NewMatches(String firstName, String bio, String images) {    //String time
        this.firstName = firstName;
        this.bio = bio;
        this.images = images;
        //this.time = time
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

   /* @ServerTimestamp
    public Date getTimestamp() { return mTimestamp; }

    public void setTimestamp(Date timestamp) { mTimestamp = timestamp; }*/

/*    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/

/*    @ServerTimestamp
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/
}

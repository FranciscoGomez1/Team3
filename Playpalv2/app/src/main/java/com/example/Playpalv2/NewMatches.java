package com.example.Playpalv2;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class NewMatches {
    private String firstName, bio, images; //time

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

    public String getImage() {
        return images;
    }

    public void setImage(String images) {
        this.images = images;
    }

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

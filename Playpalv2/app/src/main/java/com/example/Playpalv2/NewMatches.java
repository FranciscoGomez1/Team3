package com.example.Playpalv2;

import android.net.Uri;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class NewMatches {
  private String firstName, bio, time, images;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String album) {
        this.images = images;
    }

    public NewMatches() {}

    public NewMatches(String bio, String firstName, String images, String time) {    //String time
        this.bio = bio;
        this.firstName = firstName;
        this.images = images;
        this.time = time;
    }

   /* public String getFirstName() {
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
*/
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

package com.example.Playpalv2.flipCards;


import java.net.URI;
import java.util.List;

public class DogModel {
    private String name;
    private String bio;
    List<String>  album;


    public DogModel(String name, String bio, List<String>  album) {
        this.name = name;
        this.bio = bio;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }


}

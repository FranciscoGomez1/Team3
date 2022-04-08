package com.example.Playpalv2.flipCards;


import java.net.URI;
import java.util.List;

public class DogModel {
    private String name;
    private String bio;
    private String age;
    private String breed;
    private String owner;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    private String sex;
    private String weight;
    List<String>  album;

    public DogModel(String age, String bio, String breed, List<String> album, String name, String owner, String sex, String weight){
        this.age = age;
        this.bio = bio;
        this.breed = breed;
        this.album = album;
        this.name = name;
        this.owner = owner;
        this.sex = sex;
        this.weight = weight;

    }
}

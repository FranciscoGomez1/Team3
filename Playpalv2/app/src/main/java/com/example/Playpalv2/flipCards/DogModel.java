package com.example.Playpalv2.flipCards;


import java.util.List;

public class DogModel {
/*
    private String accountCreated;
*/
  private Integer age;
  private String bio;
  private String breed;
  private Integer energyLevel;
  private List<String> images;
  private String name;
  private String owner;
  private String sex;
  private Integer weight;

  public DogModel() {
  }

  public DogModel(Integer age, String bio, String breed, Integer energyLevel, List<String> images, String name, String owner, String sex, Integer weight) {
    this.age = age;
    this.bio = bio;
    this.breed = breed;
    this.energyLevel = energyLevel;
    this.images = images;
    this.name = name;
    this.owner = owner;
    this.sex = sex;
    this.weight = weight;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public Integer getEnergyLevel() {
    return energyLevel;
  }

  public void setEnergyLevel(Integer energyLevel) {
    this.energyLevel = energyLevel;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }
}

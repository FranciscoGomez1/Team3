package com.example.Playpalv2;

public class UserFilterPreferences {
    private Integer breed;
    private Integer maxAge;
    private Integer maxDistancd;
    private Integer maxEnergy;
    private Integer maxWeight;
    private Integer minAge;
    private Integer minEngergy;
    private Integer minWeight;
    private String sex;

    public UserFilterPreferences(){};
    public UserFilterPreferences(Integer breed, Integer maxAge, Integer maxDistancd, Integer maxEnergy, Integer maxWeight, Integer minAge, Integer minEngergy, Integer minWeight, String sex) {
        this.breed = breed;
        this.maxAge = maxAge;
        this.maxDistancd = maxDistancd;
        this.maxEnergy = maxEnergy;
        this.maxWeight = maxWeight;
        this.minAge = minAge;
        this.minEngergy = minEngergy;
        this.minWeight = minWeight;
        this.sex = sex;
    }

    public Integer getBreed() {
        return breed;
    }

    public void setBreed(Integer breed) {
        this.breed = breed;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMaxDistancd() {
        return maxDistancd;
    }

    public void setMaxDistancd(Integer maxDistancd) {
        this.maxDistancd = maxDistancd;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMinEngergy() {
        return minEngergy;
    }

    public void setMinEngergy(Integer minEngergy) {
        this.minEngergy = minEngergy;
    }

    public Integer getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Integer minWeight) {
        this.minWeight = minWeight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

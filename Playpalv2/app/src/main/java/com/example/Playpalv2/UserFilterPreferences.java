package com.example.Playpalv2;


public class UserFilterPreferences {
    private String Breed;
    private Integer MaxAge;
    private Integer MaxDistance;
    private Integer MaxEnergy;
    private Integer MaxWeight;
    private Integer MinAge;
    private Integer MinEnergy;
    private Integer MinWeight;
    private String Sex;

    public UserFilterPreferences() {
    }

    public UserFilterPreferences(
            String breed, Integer maxAge, Integer maxDistance, Integer maxEnergy,
            Integer maxWeight, Integer minAge, Integer minEnergy, Integer minWeight, String sex) {
        Breed = breed;
        MaxAge = maxAge;
        MaxDistance = maxDistance;
        MaxEnergy = maxEnergy;
        MaxWeight = maxWeight;
        MinAge = minAge;
        MinEnergy = minEnergy;
        MinWeight = minWeight;
        Sex = sex;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public Integer getMaxAge() {
        return MaxAge;
    }

    public void setMaxAge(Integer maxAge) {
        MaxAge = maxAge;
    }

    public Integer getMaxDistance() {
        return MaxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        MaxDistance = maxDistance;
    }

    public Integer getMaxEnergy() {
        return MaxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        MaxEnergy = maxEnergy;
    }

    public Integer getMaxWeight() {
        return MaxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        MaxWeight = maxWeight;
    }

    public Integer getMinAge() {
        return MinAge;
    }

    public void setMinAge(Integer minAge) {
        MinAge = minAge;
    }

    public Integer getMinEnergy() {
        return MinEnergy;
    }

    public void setMinEnergy(Integer minEnergy) {
        MinEnergy = minEnergy;
    }

    public Integer getMinWeight() {
        return MinWeight;
    }

    public void setMinWeight(Integer minWeight) {
        MinWeight = minWeight;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}

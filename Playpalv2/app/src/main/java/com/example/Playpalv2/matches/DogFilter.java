package com.example.Playpalv2.matches;

import com.example.Playpalv2.UserFilterPreferences;
import com.example.Playpalv2.flipCards.DogModel;

public class DogFilter {
    private Integer dogAge;
    private Integer filterMaxAge;
    private Integer filterMinAge;

    private Integer dogEnergy;
    private Integer filterMaxEnergy;
    private Integer filterMinEnergy;

    private Integer MaxDistance;

    private Integer dogWeight;
    private Integer filterMaxWeight;
    private Integer filterMinWeight;

    private String dogSex;
    private String filterDogSex;

    private DogModel dog = new DogModel();
    private UserFilterPreferences userFilterPreferences = new UserFilterPreferences();

    public DogFilter() {

    }

    public DogFilter(DogModel dog, UserFilterPreferences userFilterPreferences) {
        this.dog = dog;
        this.userFilterPreferences = userFilterPreferences;
        initFilter();

    }

    private void initFilter(){
        dogAge = dog.getAge();
        filterMaxAge = userFilterPreferences.getMaxAge();
        filterMinAge = userFilterPreferences.getMinAge();

        dogWeight = dog.getWeight();
        filterMaxWeight = userFilterPreferences.getMaxWeight();
        filterMinWeight = userFilterPreferences.getMinWeight();

        dogEnergy = dog.getEnergyLevel();
        filterMaxEnergy = userFilterPreferences.getMaxEnergy();
        filterMinEnergy = userFilterPreferences.getMinEnergy();

    }

    public Boolean doesDogPassFilter(){


        if(isDogAgeInRange() && isDogWeightInRange() && isDogEnergyInRange()){
            return true;
        }
        return false;
    }

    public boolean isDogAgeInRange(){
        if( filterMinAge <= dogAge && dogAge <= filterMaxAge){
            return true;
        }
        return false;
    }

    public boolean isDogWeightInRange(){
        if( filterMinWeight <= dogWeight && dogWeight <= filterMaxWeight ){
            return true;
        }
        return false;
    }

    public boolean isDogEnergyInRange(){
        if (filterMinEnergy <= dogEnergy && dogEnergy <= filterMaxEnergy){
            return true;
        }
        return false;
    }

}

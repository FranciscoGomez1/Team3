package com.example.Playpalv2.models;

import com.example.Playpalv2.flipCards.DogModel;

public class CardModel {
   private DogOwnerModel dogOwner;
    private DogModel dog;

    public CardModel(DogOwnerModel dogOwner, DogModel dog) {
        this.dogOwner = dogOwner;
        this.dog = dog;
    }

    public DogOwnerModel getDogOwner() {
        return dogOwner;
    }

    public void setDogOwner(DogOwnerModel dogOwner) {
        this.dogOwner = dogOwner;
    }

    public DogModel getDog() {
        return dog;
    }

    public void setDog(DogModel dog) {
        this.dog = dog;
    }


}

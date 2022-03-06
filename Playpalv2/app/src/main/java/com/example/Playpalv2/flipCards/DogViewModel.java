package com.example.Playpalv2.flipCards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DogViewModel extends ViewModel {
    private MutableLiveData<DogModel> dogMutableLiveData;
    private MutableLiveData<DogModel> dog1MutableLiveData;
    public void init()
    {
        dogMutableLiveData = new MutableLiveData<>();
        dog1MutableLiveData = new MutableLiveData<>();

    }

    public void updateDog(DogModel dog){
        dogMutableLiveData.setValue(dog);
    }

    public void updateDog1(DogModel dog){
        dog1MutableLiveData.setValue(dog);
    }

    public MutableLiveData<DogModel> getDog(){
        return dogMutableLiveData;
    }

    public MutableLiveData<DogModel> getDog1(){
        return dog1MutableLiveData;
    }

}



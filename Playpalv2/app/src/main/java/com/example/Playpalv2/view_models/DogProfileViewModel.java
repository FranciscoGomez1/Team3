package com.example.Playpalv2.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.flipCards.DogModel;


public class DogProfileViewModel extends ViewModel
{
    private MutableLiveData<DogModel> dowProfileLiveData;
    public void init(){
        dowProfileLiveData = new MutableLiveData<>();
    }
    public void updateDogProfileView(DogModel cardModel){
        dowProfileLiveData.setValue(cardModel);
    }
    public MutableLiveData<DogModel> getDogProfile(){
        return dowProfileLiveData;
    }
}

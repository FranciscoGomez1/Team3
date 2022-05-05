package com.example.Playpalv2.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.models.DogOwnerModel;

import java.util.LinkedList;

public class WalkersViewModel extends ViewModel {
    private MutableLiveData<LinkedList<DogOwnerModel>> walkersLiveData;

    public void ini(){
        walkersLiveData = new MutableLiveData<>();

    }

    public void updateWalkers(LinkedList<DogOwnerModel> walkers){
        walkersLiveData.setValue(walkers);
    }
    public MutableLiveData<LinkedList<DogOwnerModel>> getWalkersLiveData() {
        return walkersLiveData;
    }

}

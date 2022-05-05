package com.example.Playpalv2.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.models.DogOwnerModel;

import java.util.LinkedList;

public class SittersViewModel extends ViewModel {
    private MutableLiveData<LinkedList<DogOwnerModel>> SittersLiveData;

    public void ini(){
        SittersLiveData = new MutableLiveData<>();

    }

    public void updateSitters(LinkedList<DogOwnerModel> walkers){
        SittersLiveData.setValue(walkers);
    }
    public MutableLiveData<LinkedList<DogOwnerModel>> getSittersLiveData() {
        return SittersLiveData;
    }

}

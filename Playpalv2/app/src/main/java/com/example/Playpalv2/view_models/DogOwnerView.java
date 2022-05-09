package com.example.Playpalv2.view_models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.models.DogOwnerModel;

public class DogOwnerView extends ViewModel {
    private MutableLiveData<DogOwnerModel> ownerMutableLiveData;
    private MutableLiveData<DogOwnerModel> ownerMutableLiveData1;

    public void init(){
        ownerMutableLiveData = new MutableLiveData<>();
        ownerMutableLiveData1  = new MutableLiveData<>();
    }
    public void updateOwner(DogOwnerModel ownerModel){
        ownerMutableLiveData.setValue(ownerModel);
        Log.e("Owner", ownerModel.getFirst_name());
    }
    public void updateOwner1(DogOwnerModel ownerModel){
        Log.e("Owner1", ownerModel.getFirst_name());
        ownerMutableLiveData1.setValue(ownerModel);
    }
    public MutableLiveData<DogOwnerModel> getOwner() {
        return ownerMutableLiveData;
    }
    public MutableLiveData<DogOwnerModel> getOwner1() {
        return ownerMutableLiveData1;
    }

    public DogOwnerModel getThiOwner(){
        return ownerMutableLiveData.getValue();
    }
}

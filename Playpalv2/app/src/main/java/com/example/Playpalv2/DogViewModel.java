package com.example.Playpalv2;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DogViewModel extends ViewModel {
    private MutableLiveData<DogModel> stringMutableLiveData;
    public void init()
    {
        stringMutableLiveData = new MutableLiveData<>();

    }

    public void updateDogName(DogModel dog){
        stringMutableLiveData.setValue(dog);
        Log.i("STRINGMYTABLELIVEDATA", String.valueOf(stringMutableLiveData));
    }
    public MutableLiveData<DogModel> getDogName(){
        return stringMutableLiveData;
    }

}



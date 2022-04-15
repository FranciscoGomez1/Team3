package com.example.Playpalv2.view_models;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.get_from_firestore.BuildCard;
import com.example.Playpalv2.models.CardModel;

import java.util.Queue;

public class CardsQueueViewModel extends ViewModel {
    private MutableLiveData<Queue<CardModel>> cardsQueueLiveData;
    public void init() {
        cardsQueueLiveData = new MutableLiveData<>();
        Log.e("The INT GOT CALL", "YES");
    }
    public void updateCardQueue(Queue<CardModel> queue){cardsQueueLiveData.setValue(queue);}
    public MutableLiveData<Queue<CardModel>> getQcards(){
        return cardsQueueLiveData;
    }
}

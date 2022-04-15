package com.example.Playpalv2.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Playpalv2.models.CardModel;

public class CardViewModel extends ViewModel {
    private MutableLiveData<CardModel> cardMutableLiveData;
    private MutableLiveData<CardModel> card1MutableLiveData;
    public void init()
    {
        cardMutableLiveData = new MutableLiveData<>();
        card1MutableLiveData = new MutableLiveData<>();

    }

    public void updateCard(CardModel card){
        cardMutableLiveData.setValue(card);
    }

    public void updateCard1(CardModel card){
        card1MutableLiveData.setValue(card);
    }

    public MutableLiveData<CardModel> getCard(){
        return cardMutableLiveData;
    }

    public MutableLiveData<CardModel> getCard1(){
        return card1MutableLiveData;
    }

}



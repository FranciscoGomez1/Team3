package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.models.CardModel;

import java.util.Queue;

public interface OnGotCardsListener {
    void onGotCards(Queue<CardModel> cards);
}

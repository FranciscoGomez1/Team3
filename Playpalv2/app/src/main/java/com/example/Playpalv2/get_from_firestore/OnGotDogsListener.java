package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.flipCards.DogModel;

import java.util.Queue;

public interface OnGotDogsListener {
    void onGotDogs(Queue<DogModel> dogs);
}

package com.example.Playpalv2.get_from_firestore;

import com.example.Playpalv2.models.DogOwnerModel;

import java.util.LinkedList;


public interface GotServiceProvidersListener {
     void onGotServiceProviders(LinkedList<DogOwnerModel> walkers);
}

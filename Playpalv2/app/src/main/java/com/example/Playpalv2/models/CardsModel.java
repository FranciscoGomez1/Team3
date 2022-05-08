package com.example.Playpalv2.models;

import com.example.Playpalv2.flipCards.DogModel;

import java.util.LinkedList;
import java.util.Queue;

public class CardsModel {
    private Queue<DogModel> qDogs;
    private DogModel topDog;
    private DogModel bottomDog;
    private DogModel tempDog;

    public CardsModel(Queue<DogModel> qdogs) {
        this.qDogs = qdogs;
        topDog = qdogs.poll();
        bottomDog = qdogs.poll();
    }

    public void add(DogModel dog){
        qDogs.add(dog);
    }
    public DogModel pullCard(){
        tempDog = qDogs.poll();

        updateQ(tempDog);
        return tempDog;

    }

    public DogModel peek(){
        return qDogs.peek();
    }
    private void updateQ(DogModel tempDog){
        if( tempDog != null && bottomDog != null) {
            topDog = bottomDog;
            bottomDog = tempDog;
        }else if( tempDog == null && bottomDog != null){
            topDog = bottomDog;
            bottomDog = null;
        } else if (bottomDog == null){
           topDog = null;
        }
    }
    public DogModel getTopDogCard(){
       return topDog;
    }
    public DogModel getBottomDogCard(){
        return bottomDog;
    }


}

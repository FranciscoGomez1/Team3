package com.example.Playpalv2.get_from_firestore;

import android.util.Log;

import com.example.Playpalv2.flipCards.DogModel;
import com.example.Playpalv2.models.CardModel;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.view_models.CardsQueueViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class BuildCard {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Queue<CardModel> cards = new LinkedList<>();
    private DogOwnerModel thisDogOwner;
    private DogModel dog;
    private CardsQueueViewModel qCards = new CardsQueueViewModel();

    private static BuildCard instance;

    final CollectionReference[] thisCollecRef = new CollectionReference[1];

    final CountDownLatch done = new CountDownLatch(1);

    private int cnt = 0;


    public void getCards(/*OnGotCardsListener onGotCardsListener1*/){
        getTheCards();
       // return getTheCards(/*onGotCardsListener1*/);
    }


    private void getTheCards(/*OnGotCardsListener onGotCardsListener*/) {
        Task<QuerySnapshot> collectionReference = db.collection("Dog Breeds").
                document("Bulldog").collection("Dogs")
               /* whereLessThan("Age", 9)*/.get().addOnCompleteListener(task -> {
                   List<DocumentSnapshot> snapshotList = Objects.requireNonNull(task.getResult()).getDocuments();

                   try {
                       for (DocumentSnapshot snapshot : snapshotList) {
                           Log.i("TAG", Objects.requireNonNull(snapshot.getId()));
                           DogModel dog = (new DogModel(
                                   ((Integer) Objects.requireNonNull(snapshot.get("age"))),
                                   Objects.requireNonNull(snapshot.get("bio")).toString(),
                                   Objects.requireNonNull(snapshot.get("breed")).toString(),
                                   ((Integer) Objects.requireNonNull(snapshot.get("energyLevel"))),
                                   (List<String>) snapshot.get("images"),
                                   Objects.requireNonNull(snapshot.get("name")).toString(),
                                   Objects.requireNonNull(snapshot.get("owner")).toString(),
                                   Objects.requireNonNull(snapshot.get("sex")).toString(),
                                   ((Integer) Objects.requireNonNull(snapshot.get("weight"))))
                           );
                           cnt += 1;
                         //  GetDogOwner owner = new GetDogOwner(dog.getOwner());
                           /*owner.getDogOwner(new OnGotDogOwnerListener() {
                               @Override
                               public void onGotOwner(DogOwnerModel dogOwner) {
                                   thisDogOwner = dogOwner;
                                   cards.add(new CardModel(thisDogOwner, dog));
                                   Log.e("Cards", cards.peek().getDog().getName());
                                   qCards.updateCardQueue(cards);

                                  // Log.e("Card add", cards.peek().getDogOwner().getId());

                               }
                           });*/

                       }

                   } catch (Exception e) {
                       Log.i("EXEPTION", e.getMessage());
                   }

               });

        Log.e("ONGOTOWNER", "DOES IT RUN HERE?");
        //qCards.setValue(cards);
       // return  qCards;
       // onGotCardsListener.onGotCards(cards);
    }
    void getDoggoOwner(GetDogOwner owner, DogModel doggo){
/*
         owner.setOwnerID(doggo.getOwner());
         owner.getDogOwner(new OnGotDogOwnerListener() {
             @Override
             public void onGotOwner(DogOwnerModel dogOwner) {
                 thisDogOwner = dogOwner;
                 cards.add(new CardModel(thisDogOwner, dog));
             }
         });
*/
    }
    /*void getDogOwner(GetDogOwner owner, String ownerId){
        owner.getDogOwner(new OnGotDogOwnerListener (){
            @Override
            public void onGotOwner(DogOwnerModel dogOwner) {
                thisDogOwner = dogOwner;
                Log.e("OWNER ACCOUNT CREATED", dogOwner.getAccount_created());

            }

        });
    }*/


}


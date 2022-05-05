package com.example.Playpalv2.get_from_firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetSitters extends GetServiceProviders{
    @Override
    public void fetchServiceProviders(GotServiceProvidersListener gotWalkerListener1){
        getTheServiceProviders(gotWalkerListener1);
    }

    private void getTheServiceProviders(GotServiceProvidersListener gotServiceProvidersListener) {
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        queryFirebeCollection("IsSitter",
                gotServiceProvidersListener);
    }
}

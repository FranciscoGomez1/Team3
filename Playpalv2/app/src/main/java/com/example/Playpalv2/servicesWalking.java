package com.example.Playpalv2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Playpalv2.get_from_firestore.GetServiceProviders;
import com.example.Playpalv2.get_from_firestore.GetWalkers;
import com.example.Playpalv2.models.DogOwnerModel;
import com.example.Playpalv2.view_models.WalkersViewModel;
import com.example.Playpalv2.walkers_services.WalkersAdapter;

import java.util.LinkedList;
import java.util.List;

public class servicesWalking extends Fragment {
    // Add RecyclerView member
    private RecyclerView recyclerView;
    private List<DogOwnerModel> walkers2 = new LinkedList<>();
    private GetWalkers getWalkers = new GetWalkers();
    private WalkersViewModel walkersViewModel = new WalkersViewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.services_walking, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = v.findViewById(R.id.walkersRecyclerView);
        Log.e("TEST", "YES");

        if(walkersViewModel.getWalkersLiveData().getValue() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(new WalkersAdapter(this.getContext(), walkersViewModel.getWalkersLiveData().getValue()));
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walkersViewModel.ini();
        getWalkers.fetchServiceProviders(walkers ->{
            walkersViewModel.updateWalkers(walkers);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(new WalkersAdapter(this.getContext(), walkersViewModel.getWalkersLiveData().getValue()));
        });
    }
}

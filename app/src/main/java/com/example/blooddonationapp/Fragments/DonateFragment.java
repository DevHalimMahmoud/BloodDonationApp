package com.example.blooddonationapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Activitys.DonateActivity;
import com.example.blooddonationapp.Adapters.DonateAdapter;
import com.example.blooddonationapp.R;
import com.example.blooddonationapp.ui.RequestDonation.RequestItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class DonateFragment extends Fragment {

    private FirebaseFirestore db;
    RecyclerView request_item;
    private FirestoreRecyclerAdapter request_adapter;
    LinearLayoutManager requestLayoutManager;
    protected GoogleMap mGoogleMap;
    public MapView mapView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_request_donation, container, false);


        request_item = root.findViewById(R.id.list);
        init(root.getContext());


        getRequestsList(root.getContext());


        return root;
    }

    private void init(Context root) {
        requestLayoutManager = new LinearLayoutManager(root.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        request_item.setLayoutManager(requestLayoutManager);
        db = FirebaseFirestore.getInstance();
    }

    private void getRequestsList(Context root) {
        Query query = db.collection("donation_hotspot");

        FirestoreRecyclerOptions<RequestItem> response = new FirestoreRecyclerOptions.Builder<RequestItem>()
                .setQuery(query, RequestItem.class)
                .build();

        request_adapter = new DonateAdapter(response);


        request_item.setAdapter(request_adapter);


    }


    @Override
    public void onStart() {
        super.onStart();
        request_adapter.startListening();


    }


}
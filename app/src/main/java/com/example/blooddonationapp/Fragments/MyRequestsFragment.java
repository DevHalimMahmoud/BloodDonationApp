package com.example.blooddonationapp.Fragments;

import android.content.Context;
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

import com.example.blooddonationapp.Adapters.MyRequestAdapter;
import com.example.blooddonationapp.FirebaseAuthSingleton;
import com.example.blooddonationapp.Models.MyRequestItem;
import com.example.blooddonationapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class MyRequestsFragment extends Fragment {
    private FirebaseAuthSingleton mAuth = FirebaseAuthSingleton.INSTANCE;
    private FirebaseFirestore db;
    RecyclerView request_item;
    private FirestoreRecyclerAdapter request_adapter;
    LinearLayoutManager requestLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.my_requests_fragment, container, false);


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
        Query query = db.collection("requests").whereEqualTo("user_id", mAuth.getInstance().getCurrentUser().getUid().toString());

        FirestoreRecyclerOptions<MyRequestItem> response = new FirestoreRecyclerOptions.Builder<MyRequestItem>()
                .setQuery(query, MyRequestItem.class)
                .build();

        request_adapter = new MyRequestAdapter(response);
        request_item.setAdapter(request_adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        request_adapter.startListening();

    }
}
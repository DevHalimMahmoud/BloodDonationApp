package com.example.blooddonationapp.ui.MyRequests;

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

import com.example.blooddonationapp.FirebaseAuthSingleton;
import com.example.blooddonationapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

        request_adapter = new FirestoreRecyclerAdapter<MyRequestItem, MyRequestHolder>(response) {
            @Override
            public void onBindViewHolder(MyRequestHolder holder, int position, MyRequestItem myrequestitem) {


                holder.amount.setText(myrequestitem.getAmount());
                holder.statues.setText(myrequestitem.getStatus());
                holder.type.setText(myrequestitem.getType());
                holder.reason.setText(myrequestitem.getMedical_reason());

                DocumentReference orgDocRef = db.collection("users").document(myrequestitem.getOrg_id().toString());

                orgDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                holder.org_name.setText(Objects.requireNonNull(document.get("name")).toString());


                            }


                        }
                    }
                });
                DocumentReference centerDocRef = db.collection("donation_hotspot").document(myrequestitem.getHotspot_id().toString());
                centerDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                holder.center_name.setText(Objects.requireNonNull(document.get("name")).toString());


                            }


                        }
                    }
                });

//

            }

            @Override
            public MyRequestHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.my_requests_item, group, false);

                return new MyRequestHolder(view);

            }


            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };


        request_item.setAdapter(request_adapter);


    }


    public class MyRequestHolder extends RecyclerView.ViewHolder {
        TextView center_name;
        TextView org_name;
        TextView amount;
        TextView statues, type, reason;


        public MyRequestHolder(View itemView) {
            super(itemView);


            org_name = itemView.findViewById(R.id.org_name);
            center_name = itemView.findViewById(R.id.center_name);
            amount = itemView.findViewById(R.id.amount);
            statues = itemView.findViewById(R.id.statues);
            type = itemView.findViewById(R.id.type);
            reason = itemView.findViewById(R.id.reason);

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        request_adapter.startListening();

    }
}
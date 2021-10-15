package com.example.blooddonationapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.Objects;

public class MyRequestAdapter extends FirestoreRecyclerAdapter<MyRequestItem,MyRequestAdapter.MyRequestHolder> {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyRequestAdapter(@NonNull FirestoreRecyclerOptions<MyRequestItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyRequestHolder holder, int position, @NonNull MyRequestItem myrequestitem) {


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


    }

    @Override
    public MyRequestAdapter.MyRequestHolder onCreateViewHolder(ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.my_requests_item, group, false);

        return new MyRequestHolder(view);

    }


    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("error", e.getMessage());
    }


    public static class MyRequestHolder extends RecyclerView.ViewHolder {
        TextView center_name;
        TextView org_name;
        TextView amount;
        TextView statues, type, reason;


        public MyRequestHolder(@NonNull View itemView) {
            super(itemView);


            org_name = itemView.findViewById(R.id.org_name);
            center_name = itemView.findViewById(R.id.center_name);
            amount = itemView.findViewById(R.id.amount);
            statues = itemView.findViewById(R.id.statues);
            type = itemView.findViewById(R.id.type);
            reason = itemView.findViewById(R.id.reason);

        }

    }


}

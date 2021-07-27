package com.example.blooddonationapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blooddonationapp.FirebaseAuthSingleton;
import com.example.blooddonationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class HomeFragment extends Fragment {


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuthSingleton mAuth = FirebaseAuthSingleton.INSTANCE;

    TextView name, numberOfDonations, bloodType, donate, request_donation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dataBinding(root);
        requestUserInfo();
        handleUserClicks(root);


        return root;
    }

    private void handleUserClicks(View root) {
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_Donate);
            }
        });

        request_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_Request_Donation);
            }
        });
    }

    private void requestUserInfo() {
        DocumentReference docRef = db.collection("users").document(Objects.requireNonNull(mAuth.getInstance().getUid()));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        name.setText(Objects.requireNonNull(document.get("name")).toString());
                        numberOfDonations.setText(Objects.requireNonNull(document.get("num_of_donation")).toString());
                        bloodType.setText(Objects.requireNonNull(document.get("blood_type")).toString());
                    }


                }
            }
        });
    }

    private void dataBinding(View root) {
        name = root.findViewById(R.id.textView9);
        numberOfDonations = root.findViewById(R.id.textView14);
        bloodType = root.findViewById(R.id.textView11);
        request_donation = root.findViewById(R.id.request_donation);
        donate = root.findViewById(R.id.donate);
    }

}
package com.example.blooddonationapp.ui.RequsetDonation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.blooddonationapp.R;

public class RequsetDonationFragment extends Fragment {

    private RequsetDonationViewModel RequsetDonationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RequsetDonationViewModel =
                new ViewModelProvider(this).get(RequsetDonationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_request_donation, container, false);
        final TextView textView = root.findViewById(R.id.text_RequestDonation);
        RequsetDonationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
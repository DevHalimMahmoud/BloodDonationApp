package com.example.blooddonationapp.ui.RequsetDonation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequsetDonationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequsetDonationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Requset Donation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
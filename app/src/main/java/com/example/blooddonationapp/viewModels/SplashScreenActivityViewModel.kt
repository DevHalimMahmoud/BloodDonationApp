package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.getCurrentUser
import com.example.blooddonationapp.services.getCurrentUserId
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivityViewModel : ViewModel() {

    val currentUser :FirebaseUser? by lazy { getCurrentUser() }

}
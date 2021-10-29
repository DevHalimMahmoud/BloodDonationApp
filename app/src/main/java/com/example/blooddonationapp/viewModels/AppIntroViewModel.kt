package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.getCurrentUser
import com.google.firebase.auth.FirebaseUser

class AppIntroViewModel : ViewModel() {
    val currentUser: FirebaseUser? by lazy { getCurrentUser() }
}
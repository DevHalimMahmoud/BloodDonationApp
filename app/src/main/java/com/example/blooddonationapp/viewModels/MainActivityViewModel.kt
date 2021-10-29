package com.example.blooddonationapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.getCurrentUserId
import com.example.blooddonationapp.services.getUserEmail
import com.example.blooddonationapp.services.getUserName
import com.example.blooddonationapp.utils.FirebaseAuthSingleton

class MainActivityViewModel : ViewModel() {
    var uid: String? = getCurrentUserId()

    private val userName: MutableLiveData<String> = getUserName()
    val userNameLiveData: LiveData<String> = userName
    var email: String? = getUserEmail()
    fun signOut() {
        FirebaseAuthSingleton.instance?.signOut()
    }

}
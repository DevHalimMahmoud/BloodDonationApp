package com.example.blooddonationapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.firebase.firestore.FirebaseFirestore

class MainActivityViewModel : ViewModel() {
    private val mAuth = FirebaseAuthSingleton
    private val db = FirebaseFirestore.getInstance()


    private val userName = MutableLiveData<String>().apply {
        mAuth.instance!!.uid?.let {
            db.collection("users").document(
                it
            )
        }?.addSnapshotListener { value, e ->

            setValue(value?.get("name").toString())
        }
    }
    val userNameLiveData: LiveData<String> = userName
    private val email: String by lazy {

        mAuth.instance!!.currentUser?.email.toString()
    }

    fun getUserEmail(): String {
        return email
    }
    fun getCurrentUser(): String {
        return mAuth.instance!!.currentUser!!.uid.toString()
    }
    fun signOut(){
        mAuth.instance!!.signOut()
    }

}
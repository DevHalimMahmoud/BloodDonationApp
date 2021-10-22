package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DonateActivityViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()
    fun addData(data: MutableMap<String, Any?>): Task<DocumentReference> {

        return db.collection("requests")
            .add(data).addOnSuccessListener {
                return@addOnSuccessListener
            }.addOnFailureListener {
                return@addOnFailureListener
            }
    }

    fun getCurrentUser(): String {


        return mAuth.currentUser!!.uid
    }
}
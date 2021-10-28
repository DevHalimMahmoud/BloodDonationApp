package com.example.blooddonationapp.services

import androidx.lifecycle.MutableLiveData
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

fun addDonationData(data: HashMap<String, Any?>): Task<DocumentReference> {

    return FirebaseFirestore.getInstance().collection("requests")
        .add(data).addOnSuccessListener {
            return@addOnSuccessListener
        }.addOnFailureListener {
            return@addOnFailureListener
        }
}

fun getUserName(): MutableLiveData<String> {
    return MutableLiveData<String>().apply {


        val db = FirebaseFirestore.getInstance()
        FirebaseAuthSingleton.instance?.uid.let {
            db.collection("users").document(
                it!!
            )
        }.addSnapshotListener { value, e ->

            setValue(value?.get("name").toString())

        }
    }

}
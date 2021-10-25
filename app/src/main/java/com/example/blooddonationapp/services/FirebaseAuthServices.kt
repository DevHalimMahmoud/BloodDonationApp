package com.example.blooddonationapp.services

import androidx.lifecycle.MutableLiveData
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


fun getCurrentUser(): String {

    return FirebaseAuthSingleton.instance?.uid.toString()
}

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

fun getUserEmail(): String {

    return FirebaseAuthSingleton.instance?.currentUser?.email.toString()
}


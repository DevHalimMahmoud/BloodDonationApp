package com.example.blooddonationapp.services

import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.firebase.auth.FirebaseUser


fun getCurrentUserId(): String? {

    return FirebaseAuthSingleton.instance?.uid
}
fun getCurrentUser(): FirebaseUser? {

    return FirebaseAuthSingleton.instance!!.currentUser
}


fun getUserEmail(): String? {

    return FirebaseAuthSingleton.instance?.currentUser?.email
}


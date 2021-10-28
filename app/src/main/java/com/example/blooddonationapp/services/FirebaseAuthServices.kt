package com.example.blooddonationapp.services

import com.example.blooddonationapp.utils.FirebaseAuthSingleton


fun getCurrentUser(): String {

    return FirebaseAuthSingleton.instance?.uid.toString()
}


fun getUserEmail(): String {

    return FirebaseAuthSingleton.instance?.currentUser?.email.toString()
}


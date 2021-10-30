package com.example.blooddonationapp.services

import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


fun getCurrentUserId(): String? {

    return FirebaseAuthSingleton.instance?.uid
}

fun getCurrentUser(): FirebaseUser? {

    return FirebaseAuthSingleton.instance!!.currentUser
}

fun sendEmailVerification(): Task<Void> {

    return getCurrentUser()!!.sendEmailVerification().addOnCompleteListener { }
}

fun getAuthInstance(): FirebaseAuth? {

    return FirebaseAuthSingleton.instance
}

fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {

    return getAuthInstance()!!.createUserWithEmailAndPassword(email, password)
}

fun getUserEmail(): String? {

    return FirebaseAuthSingleton.instance?.currentUser?.email
}


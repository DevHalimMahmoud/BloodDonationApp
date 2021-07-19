package com.example.blooddonationapp

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class AuthInstanceSingleton(firebaseApp: FirebaseApp) : FirebaseAuth(firebaseApp) {
    companion object {
        var instance: FirebaseAuth? = null
            get() {
                if (field == null) {
                    synchronized(AuthInstanceSingleton::class.java) {
                        if (field == null) {
                            field = getInstance()
                        }
                    }
                }
                return field
            }
            private set
    }
}





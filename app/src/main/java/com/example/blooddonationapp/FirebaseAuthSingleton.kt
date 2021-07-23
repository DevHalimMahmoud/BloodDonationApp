package com.example.blooddonationapp

import com.google.firebase.auth.FirebaseAuth


object FirebaseAuthSingleton {
    var instance: FirebaseAuth? = null
        get() {
            if (field == null) {
                synchronized(FirebaseAuthSingleton::class) {
                    if (field == null) {
                        field = FirebaseAuth.getInstance()
                    }
                }
            }
            return field
        }
        private set
}






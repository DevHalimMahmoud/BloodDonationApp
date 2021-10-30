package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.addUserDataToFirestore
import com.example.blooddonationapp.services.createUserWithEmailAndPassword
import com.example.blooddonationapp.services.getCurrentUser
import com.example.blooddonationapp.services.sendEmailVerification
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignupActivityViewModel : ViewModel() {
    lateinit var email: String
    lateinit var password: String
    var data: HashMap<String, Any?> = HashMap()
    var currentUser = getCurrentUser()
    val emailEmailVerificationResult: Task<Void> by lazy { sendEmailVerification() }
    val addDataResult: Task<Void> by lazy { addUserDataToFirestore(data) }
    val createUserWithEmailAndPasswordResult: Task<AuthResult> by lazy {
        createUserWithEmailAndPassword(
            email,
            password
        )
    }
}
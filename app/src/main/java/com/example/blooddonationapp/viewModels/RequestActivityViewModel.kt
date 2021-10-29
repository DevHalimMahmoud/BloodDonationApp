package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.addDonationData
import com.example.blooddonationapp.services.getCurrentUserId
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

class RequestActivityViewModel : ViewModel() {
    var uid: String? = getCurrentUserId()
    val data: HashMap<String, Any?> = HashMap()

    val addDataResult: Task<DocumentReference> by lazy { addDonationData(data) }

}
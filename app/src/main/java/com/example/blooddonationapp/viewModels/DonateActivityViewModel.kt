package com.example.blooddonationapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.blooddonationapp.services.addDonationData
import com.example.blooddonationapp.services.getCurrentUser
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

class DonateActivityViewModel : ViewModel() {

    var uid: String = getCurrentUser()
    val data: HashMap<String, Any?> = HashMap()

    val addDataResult: Task<DocumentReference> by lazy { addDonationData(data) }


}
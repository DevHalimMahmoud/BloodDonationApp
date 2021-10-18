package com.example.blooddonationapp.fragments

import com.google.firebase.firestore.FirebaseFirestore
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blooddonationapp.R
import androidx.navigation.Navigation
import java.util.*

class HomeFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuthSingleton
    var name: TextView? = null
    var numberOfDonations: TextView? = null
    var bloodType: TextView? = null
    var donate: TextView? = null
    var request_donation: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        dataBinding(root)
        requestUserInfo()
        handleUserClicks(root)
        return root
    }

    private fun handleUserClicks(root: View) {
        donate!!.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_Donate)
        }
        request_donation!!.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_nav_home_to_nav_Request_Donation)
        }
    }

    private fun requestUserInfo() {
        val docRef = db.collection("users").document(

            mAuth.instance!!.uid.toString()

        )
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    name!!.text = Objects.requireNonNull(document["name"]).toString()
                    numberOfDonations!!.text = Objects.requireNonNull(
                        document["num_of_donation"]
                    ).toString()
                    bloodType!!.text = Objects.requireNonNull(document["blood_type"]).toString()
                }
            }
        }
    }

    private fun dataBinding(root: View) {
        name = root.findViewById(R.id.textView9)
        numberOfDonations = root.findViewById(R.id.textView14)
        bloodType = root.findViewById(R.id.textView11)
        request_donation = root.findViewById(R.id.request_donation)
        donate = root.findViewById(R.id.donate)
    }
}
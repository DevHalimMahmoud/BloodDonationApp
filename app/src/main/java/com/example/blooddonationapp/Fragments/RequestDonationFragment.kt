package com.example.blooddonationapp.Fragments

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapp.Adapters.RequestDonationAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blooddonationapp.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.blooddonationapp.models.RequestItem
import com.google.firebase.firestore.Query

class RequestDonationFragment : Fragment() {
    private var db: FirebaseFirestore? = null
    var request_item: RecyclerView? = null
    private var request_adapter: RequestDonationAdapter? = null
    var requestLayoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_request_donation, container, false)
        request_item = root.findViewById(R.id.list)
        init(root.context)
        requestsList
        return root
    }

    private fun init(root: Context) {
        requestLayoutManager =
            LinearLayoutManager(root.applicationContext, LinearLayoutManager.VERTICAL, false)
        request_item!!.layoutManager = requestLayoutManager
        db = FirebaseFirestore.getInstance()
    }

    private val requestsList: Unit
        get() {
            val query: Query = db!!.collection("donation_hotspot")
            val response = FirestoreRecyclerOptions.Builder<RequestItem>()
                .setQuery(query, RequestItem::class.java)
                .build()
            request_adapter = RequestDonationAdapter(response)
            request_item!!.adapter = request_adapter
        }

    override fun onStart() {
        super.onStart()
        request_adapter!!.startListening()
    }

    override fun onPause() {
        super.onPause()
    }
}
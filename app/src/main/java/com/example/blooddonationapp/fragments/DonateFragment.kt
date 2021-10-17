package com.example.blooddonationapp.fragments

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blooddonationapp.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.blooddonationapp.models.RequestItem
import com.example.blooddonationapp.adapters.DonateAdapter
import com.google.firebase.firestore.Query

class DonateFragment : Fragment() {
    private var db: FirebaseFirestore? = null
    var request_item: RecyclerView? = null
    private var request_adapter: FirestoreRecyclerAdapter<*, *>? = null
    var requestLayoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_request_donation, container, false)
        request_item = root.findViewById(R.id.list)
        init(root.context)
        getRequestsList(root.context)
        return root
    }

    private fun init(root: Context) {
        requestLayoutManager =
            LinearLayoutManager(root.applicationContext, LinearLayoutManager.VERTICAL, false)
        request_item!!.layoutManager = requestLayoutManager
        db = FirebaseFirestore.getInstance()
    }

    private fun getRequestsList(root: Context) {
        val query: Query = db!!.collection("donation_hotspot")
        val response = FirestoreRecyclerOptions.Builder<RequestItem>()
            .setQuery(query, RequestItem::class.java)
            .build()
        request_adapter = DonateAdapter(response)
        request_item!!.adapter = request_adapter
    }

    override fun onStart() {
        super.onStart()
        request_adapter!!.startListening()
    }
}
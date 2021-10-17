package com.example.blooddonationapp.fragments

import android.content.Context
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
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
import com.example.blooddonationapp.models.MyRequestItem
import com.example.blooddonationapp.adapters.MyRequestAdapter

class MyRequestsFragment : Fragment() {
    private val mAuth = FirebaseAuthSingleton
    private var db: FirebaseFirestore? = null
    var request_item: RecyclerView? = null
    private var request_adapter: FirestoreRecyclerAdapter<*, *>? = null
    var requestLayoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.my_requests_fragment, container, false)
        request_item = root.findViewById(R.id.list)
        init(root.context)
        getRequestsList()
        return root
    }

    private fun init(root: Context) {
        requestLayoutManager =
            LinearLayoutManager(root.applicationContext, LinearLayoutManager.VERTICAL, false)
        request_item!!.layoutManager = requestLayoutManager
        db = FirebaseFirestore.getInstance()
    }

    private fun getRequestsList() {
        val query = db!!.collection("requests").whereEqualTo(
            "user_id", mAuth.instance!!.currentUser!!
                .uid
        )
        val response = FirestoreRecyclerOptions.Builder<MyRequestItem>()
            .setQuery(query, MyRequestItem::class.java)
            .build()
        request_adapter = MyRequestAdapter(response)
        request_item!!.adapter = request_adapter
    }

    override fun onStart() {
        super.onStart()
        request_adapter!!.startListening()
    }
}
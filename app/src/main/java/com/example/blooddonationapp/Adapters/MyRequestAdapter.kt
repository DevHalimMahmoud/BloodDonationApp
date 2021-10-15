package com.example.blooddonationapp.Adapters

import android.util.Log

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.blooddonationapp.models.MyRequestItem
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.example.blooddonationapp.Adapters.MyRequestAdapter.MyRequestHolder
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.blooddonationapp.R
import com.google.firebase.firestore.FirebaseFirestoreException
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import java.util.*

class MyRequestAdapter
/**
 * Create a new RecyclerView adapter that listens to a Firestore Query.  See [ ] for configuration options.
 *
 * @param options
 */
    (options: FirestoreRecyclerOptions<MyRequestItem?>) :
    FirestoreRecyclerAdapter<MyRequestItem, MyRequestHolder>(options) {
    private val db = FirebaseFirestore.getInstance()
    override fun onBindViewHolder(
        holder: MyRequestHolder,
        position: Int,
        myrequestitem: MyRequestItem
    ) {
        holder.amount.text = myrequestitem.amount
        holder.statues.text = myrequestitem.status
        holder.type.text = myrequestitem.type
        holder.reason.text = myrequestitem.medical_reason
        val orgDocRef = db.collection("users").document(myrequestitem.org_id.toString())
        orgDocRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    holder.org_name.text = Objects.requireNonNull(
                        document["name"]
                    ).toString()
                }
            }
        }
        val centerDocRef =
            db.collection("donation_hotspot").document(myrequestitem.hotspot_id.toString())
        centerDocRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    holder.center_name.text = Objects.requireNonNull(
                        document["name"]
                    ).toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, i: Int): MyRequestHolder {
        val view = LayoutInflater.from(group.context)
            .inflate(R.layout.my_requests_item, group, false)
        return MyRequestHolder(view)
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e("error", e.message!!)
    }

    inner class MyRequestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var center_name: TextView
        var org_name: TextView
        var amount: TextView
        var statues: TextView
        var type: TextView
        var reason: TextView

        init {
            org_name = itemView.findViewById(R.id.org_name)
            center_name = itemView.findViewById(R.id.center_name)
            amount = itemView.findViewById(R.id.amount)
            statues = itemView.findViewById(R.id.statues)
            type = itemView.findViewById(R.id.type)
            reason = itemView.findViewById(R.id.reason)
        }
    }
}
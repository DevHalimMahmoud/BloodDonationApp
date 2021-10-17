package com.example.blooddonationapp.adapters


import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.example.blooddonationapp.models.RequestItem
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.blooddonationapp.activities.RequestActivity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.blooddonationapp.R
import com.google.firebase.firestore.FirebaseFirestoreException
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import java.util.*

class RequestDonationAdapter(
    options: FirestoreRecyclerOptions<RequestItem?>
) : FirestoreRecyclerAdapter<RequestItem, RequestDonationAdapter.RequestHolder>(options) {
    protected var mGoogleMap: GoogleMap? = null
    var mapView: MapView? = null
    private val db = FirebaseFirestore.getInstance()
    public override fun onBindViewHolder(
        holder: RequestHolder,
        position: Int,
        requestItem: RequestItem
    ) {
        holder.textName.text = requestItem.name
        holder.aval_types.text = requestItem.most_available
        holder.setMapLocation(requestItem.location!!.latitude, requestItem.location!!.longitude)
        val docRef = db.collection("users").document(requestItem.org_id.toString())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    holder.org_name.text = Objects.requireNonNull(
                        document["name"]
                    ).toString()
                    holder.org_num.text = Objects.requireNonNull(
                        document["org_number"]
                    ).toString()
                }
            }
        }
        holder.itemView.setOnClickListener { v: View? ->
            val snapshot = snapshots.getSnapshot(holder.absoluteAdapterPosition)
            val intent = Intent(holder.itemView.context, RequestActivity::class.java)
            intent.putExtra("hotspot_id", snapshot.id)
            intent.putExtra("org_id", requestItem.org_id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, i: Int): RequestHolder {
        val view = LayoutInflater.from(group.context)
            .inflate(R.layout.request_item, group, false)
        return RequestHolder(view)
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e("error", e.message!!)
    }

    inner class RequestHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnMapReadyCallback {
        var textName: TextView
        var org_name: TextView
        var aval_types: TextView
        var org_num: TextView
        protected var mMapLocation: LatLng? = null
        fun setMapLocation(lat: Double, lon: Double) {
            mMapLocation = LatLng(lat, lon)
            if (mGoogleMap != null) {
                updateMapContents()
            }
        }

        protected fun updateMapContents() {
            mGoogleMap!!.clear()
            // Update the mapView feature data and camera position.
            mGoogleMap!!.addMarker(MarkerOptions().position(mMapLocation))
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mMapLocation, 16f)
            mGoogleMap!!.moveCamera(cameraUpdate)
        }

        override fun onMapReady(googleMap: GoogleMap) {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mGoogleMap = googleMap
            MapsInitializer.initialize(itemView.context)
            googleMap.uiSettings.isMapToolbarEnabled = false
            mGoogleMap!!.cameraPosition
            updateMapContents()
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        mMapLocation!!.latitude, mMapLocation!!.longitude
                    )
                )
            )
            mGoogleMap!!.setOnMapClickListener {
                val uri = String.format(
                    Locale.ENGLISH,
                    "geo:%f,%f",
                    mMapLocation!!.latitude,
                    mMapLocation!!.longitude
                )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                itemView.context.startActivity(intent)
            }
        }

        init {
            mapView = itemView.findViewById<View>(R.id.map) as MapView
            mapView!!.willNotCacheDrawing()
            mapView!!.getMapAsync(this)
            mapView!!.onCreate(null)
            mapView!!.onStart()
            mapView!!.onResume()
            mapView!!.onDestroy()
            textName = itemView.findViewById(R.id.name)
            org_name = itemView.findViewById(R.id.org_name)
            aval_types = itemView.findViewById(R.id.aval_types)
            org_num = itemView.findViewById(R.id.org_num)
        }
    }
}
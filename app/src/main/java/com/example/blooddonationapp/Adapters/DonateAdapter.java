package com.example.blooddonationapp.Adapters;





import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Activitys.DonateActivity;
import com.example.blooddonationapp.Fragments.DonateFragment;
import com.example.blooddonationapp.R;
import com.example.blooddonationapp.models.MyRequestItem;
import com.example.blooddonationapp.ui.RequestDonation.RequestItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class DonateAdapter extends com.firebase.ui.firestore.FirestoreRecyclerAdapter<RequestItem, DonateAdapter.RequestHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected GoogleMap mGoogleMap;
    public MapView mapView;

    public DonateAdapter(@NonNull FirestoreRecyclerOptions<RequestItem> options)
    {
        super(options);

    }

    @Override
    public void onBindViewHolder(RequestHolder holder, int position, RequestItem requestItem) {

        holder.textName.setText(requestItem.getName());

        holder.needed_types.setText(requestItem.getMost_needed());

        holder.setMapLocation(requestItem.getLocation().getLatitude(), requestItem.getLocation().getLongitude());


        DocumentReference docRef = db.collection("users").document(requestItem.getOrg_id().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        holder.org_name.setText(Objects.requireNonNull(document.get("name")).toString());
                        holder.org_num.setText(Objects.requireNonNull(document.get("org_number")).toString());

                    }


                }
            }
        });

        holder.itemView.setOnClickListener(v -> {

            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition());

            Intent intent = new Intent(holder.itemView.getContext(), DonateActivity.class);
            intent.putExtra("hotspot_id", snapshot.getId().toString());
            intent.putExtra("org_id", requestItem.getOrg_id().toString());

            holder.itemView.getContext().startActivity(intent);


        });

    }



    @Override
    public RequestHolder onCreateViewHolder(ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.request_item, group, false);

        return new RequestHolder(view);

    }


    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("error", e.getMessage());
    }


    public class RequestHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView textName;
        TextView org_name;
        TextView needed_types;
        TextView org_num;
        TextView types_text;

        protected LatLng mMapLocation;

        public RequestHolder(View itemView) {
            super(itemView);
            mapView = (MapView) itemView.findViewById(R.id.map);
            mapView.willNotCacheDrawing();
            mapView.getMapAsync(this);
            mapView.onCreate(null);
            mapView.onStart();
            mapView.onResume();
            mapView.onDestroy();

            textName = itemView.findViewById(R.id.name);
            org_name = itemView.findViewById(R.id.org_name);
            needed_types = itemView.findViewById(R.id.aval_types);
            org_num = itemView.findViewById(R.id.org_num);
            types_text = itemView.findViewById(R.id.types_text);
            types_text.setText(R.string.nedeed_types_1);
        }

        public void setMapLocation(double lat, double lon) {

            mMapLocation = new LatLng(lat, lon);

            if (mGoogleMap != null) {
                updateMapContents();

            }

        }


        protected void updateMapContents() {


            mGoogleMap.clear();
            // Update the mapView feature data and camera position.
            mGoogleMap.addMarker(new MarkerOptions().position(mMapLocation));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mMapLocation, 16f);
            mGoogleMap.moveCamera(cameraUpdate);


        }

        @Override
        public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap = googleMap;
            MapsInitializer.initialize(itemView.getContext());
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            mGoogleMap.getCameraPosition();
            updateMapContents();


            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mMapLocation.latitude, mMapLocation.longitude)));

            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng arg0) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mMapLocation.latitude, mMapLocation.longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    itemView.getContext().startActivity(intent);
                }

            });


        }


    }

}

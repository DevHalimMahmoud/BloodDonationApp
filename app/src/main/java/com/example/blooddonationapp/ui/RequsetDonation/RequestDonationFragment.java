package com.example.blooddonationapp.ui.RequsetDonation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
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
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class RequestDonationFragment extends Fragment {


    private FirebaseFirestore db;
    RecyclerView request_item;
    private FirestoreRecyclerAdapter request_adapter;
    LinearLayoutManager requestLayoutManager;
    protected GoogleMap mGoogleMap;
    public MapView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_request_donation, container, false);


        request_item = root.findViewById(R.id.list);
        init(root.getContext());


        getRequestsList(root.getContext());


        return root;
    }

    private void init(Context root) {
        requestLayoutManager = new LinearLayoutManager(root.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        request_item.setLayoutManager(requestLayoutManager);
        db = FirebaseFirestore.getInstance();
    }

    private void getRequestsList(Context root) {
        Query query = db.collection("donation_hotspot");

        FirestoreRecyclerOptions<RequestItem> response = new FirestoreRecyclerOptions.Builder<RequestItem>()
                .setQuery(query, RequestItem.class)
                .build();

        request_adapter = new FirestoreRecyclerAdapter<RequestItem, RequestHolder>(response) {
            @Override
            public void onBindViewHolder(RequestHolder holder, int position, RequestItem requestItem) {

                holder.textName.setText(requestItem.getName());

                holder.aval_types.setText(requestItem.getMost_available());

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

                    Intent intent = new Intent(getContext(), RequestForm.class);
                    intent.putExtra("hotspot_id", snapshot.getId());
                    intent.putExtra("org_id", requestItem.getOrg_id());

                    startActivity(intent);

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
        };


        request_item.setAdapter(request_adapter);


    }


    public class RequestHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView textName;
        TextView org_name;
        TextView aval_types;
        TextView org_num;


        protected LatLng mMapLocation;

        public RequestHolder(View itemView) {
            super(itemView);
            mapView = (MapView) itemView.findViewById(R.id.map);

            mapView.onCreate(null);
            mapView.getMapAsync(this);
            mapView.onResume();

            textName = itemView.findViewById(R.id.name);
            org_name = itemView.findViewById(R.id.org_name);
            aval_types = itemView.findViewById(R.id.aval_types);
            org_num = itemView.findViewById(R.id.org_num);

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
                    getActivity().startActivity(intent);
                }

            });


        }


    }


    @Override
    public void onStart() {
        super.onStart();
        request_adapter.startListening();

    }


}
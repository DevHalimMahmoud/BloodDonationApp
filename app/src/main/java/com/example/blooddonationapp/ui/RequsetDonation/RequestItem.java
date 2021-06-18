package com.example.blooddonationapp.ui.RequsetDonation;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

public class RequestItem {




    private GeoPoint location;
    private String name;
    private String most_available;
    private String org_id;
    private String most_needed;

    public RequestItem(GeoPoint location, String name, String most_available, String org_id, String most_needed) {
        this.location = location;
        this.name = name;
        this.most_available = most_available;
        this.org_id = org_id;
        this.most_needed = most_needed;
    }
    public RequestItem() {

    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMost_available() {
        return most_available;
    }

    public void setMost_available(String most_available) {
        this.most_available = most_available;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getMost_needed() {
        return most_needed;
    }

    public void setMost_needed(String most_needed) {
        this.most_needed = most_needed;
    }
}

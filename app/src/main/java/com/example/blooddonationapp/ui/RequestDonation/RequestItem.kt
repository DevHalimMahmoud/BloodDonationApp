package com.example.blooddonationapp.ui.RequestDonation

import com.google.firebase.firestore.GeoPoint

class RequestItem {
    var location: GeoPoint? = null
    var name: String? = null
    var most_available: String? = null
    var org_id: String? = null
    var most_needed: String? = null

    constructor(
        location: GeoPoint?,
        name: String?,
        most_available: String?,
        org_id: String?,
        most_needed: String?
    ) {
        this.location = location
        this.name = name
        this.most_available = most_available
        this.org_id = org_id
        this.most_needed = most_needed
    }

    constructor() {}
}
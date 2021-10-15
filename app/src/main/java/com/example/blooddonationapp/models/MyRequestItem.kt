package com.example.blooddonationapp.models

class MyRequestItem {
    var amount: String? = null
    var hotspot_id: String? = null
    var org_id: String? = null
    var medical_reason: String? = null
    var status: String? = null
    var type: String? = null
    var user_id: String? = null

    constructor(
        amount: String?,
        hotspot_id: String?,
        org_id: String?,
        medical_reason: String?,
        status: String?,
        type: String?,
        user_id: String?
    ) {
        this.amount = amount
        this.hotspot_id = hotspot_id
        this.org_id = org_id
        this.medical_reason = medical_reason
        this.status = status
        this.type = type
        this.user_id = user_id
    }

    constructor() {}
}
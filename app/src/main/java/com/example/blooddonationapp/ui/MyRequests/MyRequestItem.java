package com.example.blooddonationapp.ui.MyRequests;

public class MyRequestItem {

    private String amount, hotspot_id, org_id, reason, status, type, user_id;

    public MyRequestItem(String amount, String hotspot_id, String org_id, String reason, String status, String type, String user_id) {
        this.amount = amount;
        this.hotspot_id = hotspot_id;
        this.org_id = org_id;
        this.reason = reason;
        this.status = status;
        this.type = type;
        this.user_id = user_id;
    }

    public MyRequestItem() {


    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHotspot_id() {
        return hotspot_id;
    }

    public void setHotspot_id(String hotspot_id) {
        this.hotspot_id = hotspot_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

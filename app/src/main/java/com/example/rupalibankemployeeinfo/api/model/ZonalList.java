package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class ZonalList {
    @SerializedName("ZoneID")
    private int zoneID;
    @SerializedName("ZonalOfficeName")
    private String zonalOfficeName;


    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getZonalOfficeName() {
        return zonalOfficeName;
    }

    public void setZonalOfficeName(String zonalOfficeName) {
        this.zonalOfficeName = zonalOfficeName;
    }

    @Override
    public String toString() {
        return "ZonalList{" +
                "zoneID=" + zoneID +
                ", zonalOfficeName='" + zonalOfficeName + '\'' +
                '}';
    }
}

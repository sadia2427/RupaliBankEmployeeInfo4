package com.rblcontact.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZonalList {
    @SerializedName("zoneList")
    private List<Zone> zoneList ;

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    @Override
    public String toString() {
        return "ZonalList{" +
                "zoneList=" + zoneList +
                '}';
    }
}

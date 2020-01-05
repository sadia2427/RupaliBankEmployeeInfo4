package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class DivisionalList {
    @SerializedName("DivisionID")
    private int divisionID;
    @SerializedName("DivisionalOfficeName")

    private String divisionalOfficeName;

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionalOfficeName() {
        return divisionalOfficeName;
    }

    public void setDivisionalOfficeName(String divisionalOfficeName) {
        this.divisionalOfficeName = divisionalOfficeName;
    }

    @Override
    public String toString() {
        return "DivisionalList{" +
                "divisionID=" + divisionID +
                ", divisionalOfficeName='" + divisionalOfficeName + '\'' +
                '}';
    }
}

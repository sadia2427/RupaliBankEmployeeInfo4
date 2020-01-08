package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class BranchInfo {
    @SerializedName("officeID")
    private int officeId;
    @SerializedName("OfficeName")
    private  String officeName;
    @SerializedName("BranchCode")
    private int branchCode;

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public int getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }

    @Override
    public String toString() {
        return "BranchInfo{" +
                "officeId=" + officeId +
                ", officeName='" + officeName + '\'' +
                ", branchCode=" + branchCode +
                '}';
    }
}

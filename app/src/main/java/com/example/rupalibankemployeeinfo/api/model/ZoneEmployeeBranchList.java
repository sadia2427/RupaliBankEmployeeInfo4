package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoneEmployeeBranchList {
    @SerializedName("EmployeeList")
    private List<SearchModel> employeeLists;
    @SerializedName("BranchInfo")
    private List<BranchInfo> branchInfoLists;

    public List<SearchModel> getEmployeeLists() {
        return employeeLists;
    }

    public void setEmployeeLists(List<SearchModel> employeeLists) {
        this.employeeLists = employeeLists;
    }

    public List<BranchInfo> getBranchInfoLists() {
        return branchInfoLists;
    }

    public void setBranchInfoLists(List<BranchInfo> branchInfoLists) {
        this.branchInfoLists = branchInfoLists;
    }

    @Override
    public String toString() {
        return "ZoneEmployeeBranchList{" +
                "employeeLists=" + employeeLists +
                ", branchInfoLists=" + branchInfoLists +
                '}';
    }
}

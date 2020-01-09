package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchWiseEmployeeDetails {
    @SerializedName("EmployeeList")
    private List<SearchModel> searchModelList;

    public List<SearchModel> getSearchModelList() {
        return searchModelList;
    }

    public void setSearchModelList(List<SearchModel> searchModelList) {
        this.searchModelList = searchModelList;
    }

    @Override
    public String toString() {
        return "BranchWiseEmployeeDetails{" +
                "searchModelList=" + searchModelList +
                '}';
    }
}

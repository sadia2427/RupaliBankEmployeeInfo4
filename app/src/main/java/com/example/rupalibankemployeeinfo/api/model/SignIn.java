package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignIn {
    @SerializedName("EmployeeInfo")
    private List<EmployeeInfo> employeeInfo;
    @SerializedName("status")
    private int status;

    public List<EmployeeInfo> getEmployeeInfo()
    {
        return employeeInfo;
    }

    public void setEmployeeInfo(List<EmployeeInfo> employeeInfo)
    {
        this.employeeInfo = employeeInfo;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "employeeInfo=" + employeeInfo +
                ", status=" + status +
                '}';
    }
}

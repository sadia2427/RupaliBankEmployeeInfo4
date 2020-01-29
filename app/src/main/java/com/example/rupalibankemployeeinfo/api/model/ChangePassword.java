package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePassword {
    @SerializedName("employee")

    private List<Employee> employee ;
    @SerializedName("status")

    private boolean status;
    @SerializedName("message")

    private String message;

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "employee=" + employee +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("status")

    private boolean status;
    @SerializedName("message")
    private String message;

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
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

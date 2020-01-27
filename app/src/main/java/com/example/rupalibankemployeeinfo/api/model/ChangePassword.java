package com.example.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("message")
    private  String mMessage;

    public boolean ismStatus() {
        return mStatus;
    }

    public String getmMessage() {
        return mMessage;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "mStatus=" + mStatus +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}

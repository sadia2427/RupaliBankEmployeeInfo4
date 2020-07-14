package com.rblcontact.rupalibankemployeeinfo.api.model;

public class ForgotPasswordModel {
    private String regNo;
    private String password;
    private String conf_password;
    private String otp;

    public ForgotPasswordModel(String regNo, String password, String conf_password, String otp) {
        this.regNo = regNo;
        this.password = password;
        this.conf_password = conf_password;
        this.otp = otp;
    }
}

package com.rblcontact.rupalibankemployeeinfo.api.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmpRegNo")
    private int empRegNo;
    @SerializedName("EmpName")
    private String empName;
    @SerializedName("EmpName_BN")
    private String empNameBN;
    @SerializedName("DesignationName")
    private String designationName;
    @SerializedName("OfficeName")
    private String officeName;
    @SerializedName("Contact")
    private String contact;
    @SerializedName("EmpMobile")
    private String empMobile;
    @SerializedName("EmpEmail")
    private String empEmail;

    public int getEmpRegNo() {
        return empRegNo;
    }

    public void setEmpRegNo(int empRegNo) {
        this.empRegNo = empRegNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNameBN() {
        return empNameBN;
    }

    public void setEmpNameBN(String empNameBN) {
        this.empNameBN = empNameBN;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empRegNo=" + empRegNo +
                ", empName='" + empName + '\'' +
                ", empNameBN='" + empNameBN + '\'' +
                ", designationName='" + designationName + '\'' +
                ", officeName='" + officeName + '\'' +
                ", contact='" + contact + '\'' +
                ", empMobile='" + empMobile + '\'' +
                ", empEmail=" + empEmail +
                '}';
    }
}

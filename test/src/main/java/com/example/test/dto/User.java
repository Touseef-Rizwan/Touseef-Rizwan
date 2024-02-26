package com.example.test.dto;

public class User {
    private String userId;
    private String fullName;
    private int version;
    private String insuranceCompany;

    public User(String userId, String fullName, int version, String insuranceCompany) {
        this.userId = userId;
        this.fullName = fullName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }
}

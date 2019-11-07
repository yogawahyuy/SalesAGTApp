package com.example.salesagt.Model;

public class DoneModel {
    String id,companyName,salesName,checkStatus,income;

    public DoneModel(String id, String companyName, String salesName, String checkStatus, String income) {
        this.id = id;
        this.companyName = companyName;
        this.salesName = salesName;
        this.checkStatus = checkStatus;
        this.income = income;
    }
    public DoneModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}

package com.example.salesagt.Model;

import java.io.Serializable;

public class MyProgressModel implements Serializable {
    String id,companyName,salesName,checkStatus,income,date,uidSales;

    public MyProgressModel() {
    }

    public MyProgressModel(String companyName, String salesName, String checkStatus, String income, String date, String uidSales) {
        this.companyName = companyName;
        this.salesName = salesName;
        this.checkStatus = checkStatus;
        this.income = income;
        this.date = date;
        this.uidSales = uidSales;
    }

    public String getUidSales() {
        return uidSales;
    }

    public void setUidSales(String uidSales) {
        this.uidSales = uidSales;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

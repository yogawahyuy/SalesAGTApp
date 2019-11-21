package com.example.salesagt.Model;

public class UserModel {
    String idEmail,idPhone,name,email,numberPhone,gender,address,picture;

    public UserModel() {
    }

    public UserModel(String idEmail, String idPhone, String name, String email, String numberPhone, String gender, String address, String picture) {
        this.idEmail = idEmail;
        this.idPhone = idPhone;
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.gender = gender;
        this.address = address;
        this.picture = picture;
    }

    public String getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(String idEmail) {
        this.idEmail = idEmail;
    }

    public String getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(String idPhone) {
        this.idPhone = idPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

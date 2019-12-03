package com.example.salesagt.Model;

import java.io.Serializable;

public class NotifModel implements Serializable {
    String id,title,message,date;

    public NotifModel(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public NotifModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

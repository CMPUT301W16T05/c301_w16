package com.example.c301_w16_g5.c301_w16_g5;

import java.util.Date;

public class Notification extends GenericModel<GenericView> {
    private String message;
    private Date date;

    public Notification(String message) {
        this.message = message;
        date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}

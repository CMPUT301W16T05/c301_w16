package com.example.c301_w16_g5.c301_w16_g5;

import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * A user will receive a <code>Letter</code> if someone decides to send them
 * a message over the app's messaging system.
 *
 * @author  Alex
 * @version 1.4, 03/02/2016
 * @see     User
 */
public class Letter extends GenericModel<GenericView> {
    @JestId
    private String id;

    private String message;
    private Date date;

    private String toUsername;
    private String fromUsername;

    public Letter(String message, String toUsername, String fromUsername) {
        this.message = message;
        this.toUsername = toUsername;
        this.fromUsername = fromUsername;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }
}

package com.example.c301_w16_g5.c301_w16_g5;

import java.util.Date;

/**
 * A user will receive a <code>Notification</code> when their chickens are
 * bidded upon, or when bids they have placed are accepted/rejected.
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Bid
 * @see     NotificationsActivity
 */
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

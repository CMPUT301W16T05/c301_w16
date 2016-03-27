package com.example.c301_w16_g5.c301_w16_g5;

import io.searchbox.annotations.JestId;

/**
 * A <code>Location</code> is specified by the owner of a chicken when they
 * accept a bid on it, allowing them to specify the meeting place.  The bidder
 * will then be able to view that location.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     Bid
 * @see     ChooseLocationActivity
 * @see     ViewLocationActivity
 * @see     ChickenController
 */
public class Location {
    private double latitude;
    private double longitude;

    @JestId
    private String id;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.example.c301_w16_g5.c301_w16_g5;

import io.searchbox.annotations.JestId;

/**
 * A <code>Bid</code> can be placed by a user upon a chicken they would like to borrow, and
 * then possibly accepted by the owner of that chicken.
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     Notification
 * @see     BidsActivity
 */
public class Bid extends GenericModel<GenericView> {

    public enum BidStatus {
        UNDECIDED,
        ACCEPTED,
        REJECTED
    }

    @JestId
    private String id;

    private String bidder_username;
    private String chicken_id;
    private double amount;
    private BidStatus bidStatus;
    private Location location;

    public Bid(String bidder_username, String chicken_id, Double amount) {
        this.bidder_username = bidder_username;
        this.chicken_id = chicken_id;
        this.amount = amount;
        this.bidStatus = BidStatus.UNDECIDED;
    }

    public String getBidderUsername() {
        return bidder_username;
    }

    public String getChickenId() {
        return chicken_id;
    }

    public double getAmount() {
        return amount;
    }

    public BidStatus getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(BidStatus bidStatus) {
        this.bidStatus = bidStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

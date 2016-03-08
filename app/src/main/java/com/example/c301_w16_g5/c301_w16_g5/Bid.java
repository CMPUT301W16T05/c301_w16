package com.example.c301_w16_g5.c301_w16_g5;

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

    private String bidder_username;
    private double amount;
    private BidStatus bidStatus;
    private Location location;

    public Bid(String bidder_username, Double amount) {
        this.bidder_username = bidder_username;
        this.amount = amount;
        this.bidStatus = BidStatus.UNDECIDED;
    }

    public String getBidderUsername() {
        return bidder_username;
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
}

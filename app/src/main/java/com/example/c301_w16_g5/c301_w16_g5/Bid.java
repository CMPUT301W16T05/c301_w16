package com.example.c301_w16_g5.c301_w16_g5;

public class Bid extends GenericModel<GenericView> {

    public enum BidStatus {
        UNDECIDED,
        ACCEPTED,
        REJECTED
    }

    private User bidder;
    private double amount;
    private BidStatus bidStatus;

    public Bid(User bidder, Double amount) {
        this.bidder = bidder;
        this.amount = amount;
        this.bidStatus = BidStatus.UNDECIDED;
    }

    public User getBidder() {
        return bidder;
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
}

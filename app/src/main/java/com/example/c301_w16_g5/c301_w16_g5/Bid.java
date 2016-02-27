package com.example.c301_w16_g5.c301_w16_g5;

public class Bid extends GenericModel<GenericView> {

    private User bidder;
    private double amount;

    public Bid(User bidder, Double amount, Chicken chicken) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public User getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }
}

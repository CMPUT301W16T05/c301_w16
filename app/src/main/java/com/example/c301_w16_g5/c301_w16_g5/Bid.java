package com.example.c301_w16_g5.c301_w16_g5;

public class Bid {
    public enum Status {
        PENDING_APPROVAL,
        ACCEPTED,
        DECLINED
    }

    private Status status;
    private User bidder;
    private double amount;
    private Chicken chicken;

    public Bid(User bidder, Double amount, Chicken chicken) {
        this.bidder = bidder;
        this.amount = amount;
        this.chicken = chicken;
        status = Status.PENDING_APPROVAL;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    public Chicken getChicken() {
        return chicken;
    }
}

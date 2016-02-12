package com.example.c301_w16_g5.c301_w16_g5;

public class Bid {
    public enum Status {
        PENDING_APPROVAL,
        ACCEPTED,
        DECLINED
    }

    private Status status;

    public Bid(User owner, Double amount, Chicken chick) {

    }

    public Status getStatus() {
        return null;
    }
}

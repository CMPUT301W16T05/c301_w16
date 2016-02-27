package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by Robin on 2016-02-10.
 */
public class Chicken extends GenericModel<GenericView> {
    public enum ChickenStatus {
        AVAILABLE,
        BORROWED,
        NOT_AVAILABLE
    }

    private String name;
    private String description;
    private ChickenStatus chickenStatus;
    private User owner;
    private ArrayList<Bid> bids;

    public Chicken() {
        bids = new ArrayList<Bid>();
    }

    public Chicken(String name, String description, ChickenStatus chickenStatus, User owner) {
        this.name = name;
        this.description = description;
        this.chickenStatus = chickenStatus;
        this.owner = owner;
        this.bids = new ArrayList<Bid>();
    }

    public void update(String name, String description, ChickenStatus chickenStatus) {
        this.name = name;
        this.description = description;
        this.chickenStatus = chickenStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChickenStatus getChickenStatus() {
        return chickenStatus;
    }

    public void setChickenStatus(ChickenStatus chickenStatus) {
        this.chickenStatus = chickenStatus;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public Bid getAcceptedBid() throws Exception {
        ArrayList<Bid> bids = getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return bid;
            }
        }

        throw new Exception("No accepted bid exists.");
    }
}

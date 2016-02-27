package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by Robin on 2016-02-10.
 */
public class Chicken extends GenericModel<GenericView> {
    public enum Status {
        AVAILABLE,
        BORROWED,
        NOT_AVAILABLE
    }

    private String name;
    private Status status;
    private User owner;
    private String description;
    private ArrayList<Bid> bids;

    public Chicken() {
        bids = new ArrayList<Bid>();
    }

    public Chicken(String name, String description, Status status) {
        this.name = name;
        this.owner = User.getCurrentUser();
        this.description = description;
        this.status = status;

        bids = new ArrayList<Bid>();
    }

    public void update(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public void acceptBid(Bid bid) {
        // TODO: accept the bid
    }

    public void declineBid(Bid bid) {
        // TODO: decline the bid
    }
}

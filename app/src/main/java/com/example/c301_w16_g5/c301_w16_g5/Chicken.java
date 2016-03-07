package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * A <code>Chicken</code> is the item of trade around which the application is
 * based.  Users borrow chickens from other users who own them, determined by
 * a bidding system.  Chickens are searchable in a database.
 *
 * @author  Robin
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Bid
 * @see     Photograph
 * @see     SearchController
 * @see     ChickenController
 * @see     ChickenActivity
 * @see     AddChickenActivity
 * @see     EditChickenActivity
 * @see     OwnerChickenProfileActivity
 * @see     BorrowerChickenProfileActivity
 */
public class Chicken extends GenericModel<GenericView> {
    public enum ChickenStatus {
        AVAILABLE,
        BORROWED,
        BIDDED,
        NOT_AVAILABLE
    }

    private String name;
    private String description;
    private ChickenStatus chickenStatus;
    private User owner;
    private User borrower;
    private ArrayList<Bid> bids;
    private Photograph photo;

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


    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Photograph getPhoto() {
        return photo;
    }

    public void setPhoto(Photograph photo) {
        this.photo = photo;
    }
}

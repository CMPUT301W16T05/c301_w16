package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

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
    @JestId
    protected String id;

    private String name;
    private String description;
    private ChickenStatus chickenStatus;
    private String owner_username;
    private String borrower_username;
    private ArrayList<Bid> bids;
    private Photograph photo;

    protected Chicken() {
        bids = new ArrayList<Bid>();
    }

    public Chicken(String name, String description, String owner_username) {
        this.name = name;
        this.description = description;
        this.chickenStatus = ChickenStatus.AVAILABLE;
        this.owner_username = owner_username;
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

    public String getOwnerUsername() {
        return owner_username;
    }

    public void setOwnerUsername(String owner_username) {
        this.owner_username = owner_username;
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


    public String getBorrowerUsername() {
        return borrower_username;
    }

    public void setBorrowerUsername(String borrower_username) {
        this.borrower_username = borrower_username;
    }

    public Photograph getPhoto() {
        return photo;
    }

    public void setPhoto(Photograph photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

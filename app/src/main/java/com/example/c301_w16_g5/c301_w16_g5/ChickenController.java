package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;

import java.util.ArrayList;

import io.searchbox.core.Search;

/**
 * This class is responsible for operating on the <code>Chicken</code> model to
 * perform complicated actions.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     AddChickenActivity
 * @see     EditChickenActivity
 * @see     OwnerChickenProfileActivity
 * @see     BorrowerChickenProfileActivity
 */
public class ChickenController {
    public ChickenController() {
    }

    // Chickens
    public ArrayList<Chicken> getAllChickensInPossession() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> possessed_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if (((chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED) &&
                (!chicken.getOwnerUsername().equals(current_user.getUsername()))) ||
                ((chicken.getChickenStatus() != Chicken.ChickenStatus.BORROWED) &&
                (chicken.getOwnerUsername().equals(current_user.getUsername())))){

                possessed_chickens.add(chicken);
            }
        }

        return possessed_chickens;
    }

    // chicken lending management
    public ArrayList<Chicken> getBorrowedFromOthers() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = getAllChickensInPossession();
        ArrayList<Chicken> borrowed_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED) &&
                (!chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                borrowed_chickens.add(chicken);
            }
        }

        return borrowed_chickens;
    }

    public ArrayList<Chicken> getBorrowedFromMe() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = getAllChickensInPossession();
        ArrayList<Chicken> borrowed_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED) &&
                    (chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                borrowed_chickens.add(chicken);
            }
        }

        return borrowed_chickens;
    }

    public ArrayList<Chicken> getChickensWithBids() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = getAllChickensInPossession();
        ArrayList<Chicken> bidded_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BIDDED) &&
                    (chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                bidded_chickens.add(chicken);
            }
        }

        return bidded_chickens;
    }

    public void saveChicken(Chicken chicken) {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        AsyncTask<Chicken, Void, Chicken> task = new SearchController.AddChickenTask();
        task.execute(chicken);
        current_user.addChicken(chicken);
    }

    // Bids
    public ArrayList<Bid> getAllActiveBids(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();
        ArrayList<Bid> active_bids = new ArrayList<Bid>();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.UNDECIDED) {
                active_bids.add(bid);
            }
        }

        return active_bids;
    }

    public double getHighestBid(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();
        double highest_bid = 0;

        for (Bid bid : bids) {
            if ((bid.getBidStatus() == Bid.BidStatus.UNDECIDED)
                && (bid.getAmount() > highest_bid)) {
                highest_bid = bid.getAmount();
            }
        }

        return highest_bid;
    }

    public boolean hasAcceptedBid(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return true;
            }
        }

        return false;
    }

    public Bid getAcceptedBid(Chicken chicken) throws ChickenException {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return bid;
            }
        }

        throw new ChickenException("No accepted bid exists.");
    }

    public void acceptBid(Bid bid, Chicken chicken) {
        bid.setBidStatus(Bid.BidStatus.ACCEPTED);
        chicken.getBids().clear();
        chicken.getBids().add(bid);
    }

    public void rejectBid(Bid bid) {
        bid.setBidStatus(Bid.BidStatus.REJECTED);
    }

    public void putBidOnChicken(Bid bid, Chicken chicken) throws ChickenException {
        if (bid.getAmount() < getHighestBid(chicken)) {
            throw new ChickenException("Bid is not high enough");
        }

        chicken.getBids().add(bid);
    }
}

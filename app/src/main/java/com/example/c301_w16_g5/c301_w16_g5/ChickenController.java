package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;

import java.util.ArrayList;

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
    public ArrayList<Chicken> getAllChickensInMyPossession() {
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

    public ArrayList<Chicken> getChickensBorrowedFromOthers() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> borrowed_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED) &&
                (!chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                borrowed_chickens.add(chicken);
            }
        }

        return borrowed_chickens;
    }

    public ArrayList<Chicken> getMyOwnedChickens() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> owned_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if (chicken.getOwnerUsername().equals(current_user.getUsername())) {
                owned_chickens.add(chicken);
            }
        }

        return owned_chickens;
    }

    public ArrayList<Chicken> getChickensLentByMe() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> borrowed_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED) &&
                    (chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                borrowed_chickens.add(chicken);
            }
        }

        return borrowed_chickens;
    }

    public ArrayList<Chicken> getMyChickensWithBids() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> bidded_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BIDDED) &&
                    (chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                bidded_chickens.add(chicken);
            }
        }

        return bidded_chickens;
    }

    public ArrayList<Chicken> getChickensBiddedOnByMe() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> all_chickens = current_user.getMyChickens();
        ArrayList<Chicken> my_bidded_chickens = new ArrayList<Chicken>();

        for (Chicken chicken : all_chickens) {
            if ((chicken.getChickenStatus() == Chicken.ChickenStatus.BIDDED) &&
                    (!chicken.getOwnerUsername().equals(current_user.getUsername()))) {
                my_bidded_chickens.add(chicken);
            }
        }

        return my_bidded_chickens;
    }

    public void saveChickenForMe(Chicken chicken) {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ChickBidsApplication.getSearchController().addChickenToDatabase(chicken);
        current_user.addChicken(chicken);
    }

    public void updateChickenForMe(Chicken updated_chicken) {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        ChickBidsApplication.getSearchController().updateChickenInDatabase(updated_chicken);
        current_user.deleteChickenForId(updated_chicken.getId());
        current_user.addChicken(updated_chicken);
    }

    // Bids
    public ArrayList<Bid> getAllActiveBidsForChicken(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();
        ArrayList<Bid> active_bids = new ArrayList<Bid>();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.UNDECIDED) {
                active_bids.add(bid);
            }
        }

        return active_bids;
    }

    public double getHighestBidForChicken(Chicken chicken) {
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

    public boolean chickenHasAcceptedBid(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return true;
            }
        }

        return false;
    }

    public Bid getAcceptedBidForChicken(Chicken chicken) throws ChickenException {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return bid;
            }
        }

        throw new ChickenException("No accepted bid exists.");
    }

    public void acceptBidForChicken(Bid bid, Chicken chicken) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        bid.setBidStatus(Bid.BidStatus.ACCEPTED);

        for (Bid b : chicken.getBids()) {
            if (b != bid) {
                searchController.removeBidFromDatabase(b.getId());
            }
        }
        chicken.getBids().clear();
        chicken.getBids().add(bid);
    }

    public void rejectBidForChicken(Bid bid) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        bid.setBidStatus(Bid.BidStatus.REJECTED);
        searchController.updateBidInDatabase(bid);
    }

    public void putBidOnChicken(Bid bid, Chicken chicken) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();

        if (bid.getAmount() < getHighestBidForChicken(chicken)) {
            throw new ChickenException("Bid is not high enough");
        }

        searchController.addBidToDatabase(bid);
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        chicken.getBids().add(bid);
        current_user.addChicken(chicken);
    }

    // Input Validation
    public static boolean validateChickenName(String name) {
        return UserController.genericValidateLettersNumbersOnly(name);
    }

    public static boolean validateChickenDescription(String description) {
        return description.length() > 0;
    }

    public static boolean validateChickenStatus(Chicken.ChickenStatus status) {
        // http://stackoverflow.com/questions/1509614/check-valid-enum-values-before-using-enum
        for (Chicken.ChickenStatus s : Chicken.ChickenStatus.values()) {
            if (s == status) return true;
        }
        return false;
    }

}

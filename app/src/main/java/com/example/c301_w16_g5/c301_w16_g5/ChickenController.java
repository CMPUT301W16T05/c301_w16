package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.widget.Toast;

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
 * @see     MyChickenDisplayProfileActivity
 * @see     OthersChickenDisplayProfileActivity
 */
public class ChickenController {
    private Chicken currentChicken;

    public ChickenController() {}

    public Chicken getCurrentChicken() {
        return currentChicken;
    }

    public void setCurrentChicken(Chicken currentChicken) {
        this.currentChicken = currentChicken;
    }

    /* Chickens */

    /**
     * Finds all chickens that the current user has in their possession.
     * Includes chickens that the user owns and is not lending, as well as
     * chickens they are borrowing.
     *
     * @return  list of possessed chickens
     */
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

    /**
     * Finds all chickens that the current user is borrowing from other users.
     *
     * @return  list of borrowed chickens
     */
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

    /**
     * Finds all chickens that the current user owns, whether they are lent out
     * or not.
     *
     * @return  list of owned chickens
     */
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

    /**
     * Finds all chickens that the current user is lending out to other users.
     *
     * @return  list of lent chickens
     */
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

    /**
     * Finds all chickens that the current user owns and has in their
     * possession, with bids on them.
     *
     * @return  list of bidded chickens
     */
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

    /**
     * Finds all chickens that the current user has pending bids upon.
     *
     * @return  list of bidded chickens
     */
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

    /**
     * Saves a new chicken to the Elasticsearch database.
     *
     * @param   chicken the new chicken to save
     */
    public void saveChickenForMe(Chicken chicken) {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        chicken = ChickBidsApplication.getSearchController().addChickenToDatabase(chicken);
        current_user.addChicken(chicken);
        ChickBidsApplication.getUserController().updateUser(current_user);
    }

    /**
     * Updates an existing chicken in the Elasticsearch database.
     *
     * @param   updated_chicken the chicken to update
     */
    public void updateChickenForMe(Chicken updated_chicken) {
        User user = ChickBidsApplication.getUserController().getUser(updated_chicken.getOwnerUsername());
        user.deleteChickenForId(updated_chicken.getId());
        updated_chicken = ChickBidsApplication.getSearchController().updateChickenInDatabase(updated_chicken);
        user.addChicken(updated_chicken);
    }

    /**
     * Updates the database with offline chicken push requests.
     *
     */
    public void pushOfflineChickens() {
        User user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> chickens = ChickBidsApplication.getSearchController().pushOfflineChickensToDatabase();

        for (Chicken chicken : chickens) {
            user.addChicken(chicken);
        }
    }

    /**
     * Deletes a  chicken from the Elasticsearch database, if it exists.
     *
     * @param   chicken the chicken to delete
     */
    public void removeChickenForMe(Chicken chicken){
        User user = ChickBidsApplication.getUserController().getCurrentUser();

        for (Bid b : chicken.getBids()) {
            ChickBidsApplication.getSearchController().removeBidFromDatabase(b.getId());
        }

        user.deleteChickenForId(chicken.getId());
        ChickBidsApplication.getSearchController().removeChickenFromDatabase(chicken.getId());
    }

    /* Bids */

    /**
     * Retrieves all bids on a particular chicken.
     *
     * @param   chicken the chicken to get bids for
     * @return  list of bids
     */
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

    /**
     * Retrieves highest bid on a particular chicken.
     *
     * @param   chicken the chicken to get bid for
     * @return  largest-valued bid
     */
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

    /**
     * Determines whether a specified chicken has an accepted bid.
     *
     * @param   chicken the chicken to check for accepted bid
     * @return  true if a bid has been accepted, false otherwise
     */
    public boolean chickenHasAcceptedBid(Chicken chicken) {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the bid for a particular chicken that was accepted.
     *
     * @param   chicken the chicken from which to get bid
     * @return  accepted bid
     */
    public Bid getAcceptedBidForChicken(Chicken chicken) throws ChickenException {
        ArrayList<Bid> bids = chicken.getBids();

        for (Bid bid : bids) {
            if (bid.getBidStatus() == Bid.BidStatus.ACCEPTED) {
                return bid;
            }
        }

        throw new ChickenException("No accepted bid exists.");
    }

    /**
     * Accepts a bid on a chicken.
     *
     * @param   bid the bid that the user has chosen to accept
     */
    public void acceptBidForChicken(Bid bid) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        User current_user = userController.getCurrentUser();

        Chicken chicken = getChickenForBidForCurrentUser(bid);
        current_user.deleteChickenForId(chicken.getId());

        if (!userController.getCurrentUser().getUsername().equals(chicken.getOwnerUsername())) {
            throw new ChickenException("Cannot accept bid if not the owner");
        }

        bid.setBidStatus(Bid.BidStatus.ACCEPTED);

        for (Bid b : chicken.getBids()) {
            if ((b.getBidStatus() == Bid.BidStatus.UNDECIDED) && (b != bid)) {
                User user = searchController.getUserFromDatabase(b.getBidderUsername());
                removeChickenForBidFromUser(user, b);
                searchController.removeBidFromDatabase(b.getId());
                searchController.updateUserInDatabase(user);
            }
        }

        bid = searchController.updateBidInDatabase(bid);
        chicken.getBids().clear();
        chicken.addBid(bid);
        chicken.setChickenStatus(Chicken.ChickenStatus.BORROWED);
        chicken.setBorrowerUsername(bid.getBidderUsername());
        chicken = searchController.updateChickenInDatabase(chicken);
        current_user.addChicken(chicken);

        userController.updateUser(userController.getCurrentUser());
    }

    private Chicken getChickenForBidForCurrentUser(Bid bid) throws ChickenException {
        ArrayList<Chicken> chickens = ChickBidsApplication.getUserController().getCurrentUser().getMyChickens();
        for (Chicken c : chickens) {
            if (c.getBids().contains(bid)) {
                return c;
            }
        }

        throw new ChickenException("No chicken exists for bid");
    }

    /**
     * Rejects a bid on a chicken.
     *
     * @param   bid the bid that the user has chosen to reject
     */
    public void rejectBidForChicken(Bid bid) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        Chicken chicken = getChickenForBidForCurrentUser(bid);

        if (!userController.getCurrentUser().getUsername().equals(chicken.getOwnerUsername())) {
            throw new ChickenException("Cannot accept bid if not the owner");
        }

        bid.setBidStatus(Bid.BidStatus.REJECTED);
        searchController.updateBidInDatabase(bid);

        User user = searchController.getUserFromDatabase(bid.getBidderUsername());
        removeChickenForBidFromUser(user, bid);
    }

    private void removeChickenForBidFromUser(User user, Bid bid) {
        user.deleteChickenForId(bid.getChickenId());
        ChickBidsApplication.getSearchController().updateUserInDatabase(user);
    }

    /**
     * Places a bid on a chicken.
     *
     * @param   bid the bid that a non-owner user has placed
     * @param   chicken the chicken the bid is being places upon
     */
    public void putBidOnChicken(Bid bid, Chicken chicken) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        if (bid.getAmount() < getHighestBidForChicken(chicken)) {
            throw new ChickenException("Bid is not high enough");
        } else if ((chicken.getChickenStatus() == Chicken.ChickenStatus.NOT_AVAILABLE)
            || (chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED)) {
            throw new ChickenException("Bid cannot be placed on borrowed or not available chickens");
        }

        bid = searchController.addBidToDatabase(bid);
        chicken.addBid(bid);
        chicken.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        updateChickenForMe(chicken);

        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        if (current_user.hasChicken(chicken.getId())) {
            current_user.deleteChickenForId(chicken.getId());
        }
        current_user.addChicken(chicken);
        userController.updateUser(current_user);

        addNotificationForBid(bid);
    }

    /**
     * Returns a chicken that was borrowed to its owner.  Removes it from the
     * borrower's possession, and makes it available for bidding again.
     *
     * @param   chicken the chicken that is being returned
     */
    public void returnChickenToOwner(Chicken chicken) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        User current_user = userController.getCurrentUser();

        for (Bid b : chicken.getBids()) {
            searchController.removeLocationFromDatabase(b.getLocation().getId());
            searchController.removeBidFromDatabase(b.getId());
        }

        chicken.getBids().clear();
        chicken.setChickenStatus(Chicken.ChickenStatus.AVAILABLE);
        chicken = searchController.updateChickenInDatabase(chicken);

        current_user.deleteChickenForId(chicken.getId());
        userController.updateUser(current_user);
    }

    /* Notifications */

    /**
     * Assigns a notification containing a specified message to a user.
     *
     * @param   username    the username of user being notified
     * @param   message the message to have in the notification
     */
    public void addNotification(String username, String message) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        User user = searchController.getUserFromDatabase(username);

        Notification notification = new Notification(Notification.notificationMessageBuilderForMailbox(current_user.getUsername(), message));
        notification = searchController.addNotificationToDatabase(notification);

        user.addNotification(notification);
        searchController.updateUserInDatabase(user);
    }

    /**
     * Makes a notification to alert a user of a bid.
     *
     * @param   bid the bid that the notification is attached to
     */
    public void addNotificationForBid(Bid bid) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        Chicken chicken = searchController.getChickenFromDatabase(bid.getChickenId());
        User user = searchController.getUserFromDatabase(chicken.getOwnerUsername());

        Notification notification = new Notification(Notification.notificationMessageBuilderForBid(bid));
        notification = searchController.addNotificationToDatabase(notification);

        user.addNotification(notification);
        searchController.updateUserInDatabase(user);
    }

    /**
     * Deletes a notification for the current user.
     *
     * @param   notification    the notification to delete
     */
    public void removeNotificationForMe(Notification notification) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();

        current_user.deleteNotificationForId(notification.getId());
        searchController.removeNotificationFromDatabase(notification.getId());
    }

    /**
     * Determines if the current user has any notifications.
     *
     * @return   true if notifications exist for user, false otherwise
     */
    public boolean userHasNotifications() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        return !current_user.getNotifications().isEmpty();
    }

    /**
     * Alerts the current user if they have any notifications, via a Toast
     *
     * @param   context the context to make the Toast in
     */
    public void popupNotificationToast(Context context) {
        Toast toast = Toast.makeText(context, R.string.pending_notifications, Toast.LENGTH_LONG);
        if (userHasNotifications())
            toast.show();
    }

    /* Location */

    /**
     * Assigns a location to a bid.
     *
     * @param   bid the bid that location is being attached to
     * @param   location    the location to receive the bidded chicken at
     */
    public void addLocationForBid(Bid bid, Location location) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        location = searchController.addLocationToDatabase(location);
        bid.setLocation(location);
        searchController.updateBidInDatabase(bid);
    }

    /* Input Validation */

    /**
     * Checks that a chicken name is strictly alphanumeric.
     *
     * @param   name    the chicken name to check
     * @return  true if valid chicken name, false otherwise
     */
    static boolean validateChickenName(String name) {
        return UserController.genericValidateLettersNumbersOnly(name);
    }

    /**
     * Checks that a chicken description is valid.
     *
     * @param   description the chicken description to check
     * @return  true if valid chicken description, false otherwise
     */
    public static boolean validateChickenDescription(String description) {
        return description.length() > 0;
    }

    /**
     * Checks that a chicken status is valid.
     *
     * @param   status  the chicken status to check
     * @return  true if valid chicken status, false otherwise
     */
    public static boolean validateChickenStatus(Chicken.ChickenStatus status) {
        // http://stackoverflow.com/questions/1509614/check-valid-enum-values-before-using-enum
        for (Chicken.ChickenStatus s : Chicken.ChickenStatus.values()) {
            if (s == status) return true;
        }
        return false;
    }

}

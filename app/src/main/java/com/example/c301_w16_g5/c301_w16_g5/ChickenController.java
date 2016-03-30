package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.util.Log;
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

    public ChickenController() {
    }

    public Chicken getCurrentChicken() {
        return currentChicken;
    }

    public void setCurrentChicken(Chicken currentChicken) {
        this.currentChicken = currentChicken;
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
        chicken = ChickBidsApplication.getSearchController().addChickenToDatabase(chicken);
        current_user.addChicken(chicken);
        ChickBidsApplication.getUserController().updateUser(current_user);
    }

    public void updateChickenForMe(Chicken updated_chicken) {
        User user = ChickBidsApplication.getUserController().getUser(updated_chicken.getOwnerUsername());
        user.deleteChickenForId(updated_chicken.getId());
        updated_chicken = ChickBidsApplication.getSearchController().updateChickenInDatabase(updated_chicken);
        user.addChicken(updated_chicken);
    }

    public void pushOfflineChickens() {
        User user = ChickBidsApplication.getUserController().getCurrentUser();
        ArrayList<Chicken> chickens = ChickBidsApplication.getSearchController().pushOfflineChickensToDatabase();

        for (Chicken chicken : chickens) {
            user.addChicken(chicken);
        }
    }

    public void removeChickenForMe(Chicken chicken){
        User user = ChickBidsApplication.getUserController().getCurrentUser();
        user.deleteChickenForId(chicken.getId());
        ChickBidsApplication.getSearchController().removeChickenFromDatabase(chicken.getId());
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

    public void acceptBidForChicken(Bid bid) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        Chicken chicken = getChickenForBidForCurrentUser(bid);

        if (!userController.getCurrentUser().getUsername().equals(chicken.getOwnerUsername())) {
            throw new ChickenException("Cannot accept bid if not the owner");
        }

        bid.setBidStatus(Bid.BidStatus.ACCEPTED);

        for (Bid b : chicken.getBids()) {
            if (b != bid) {
                User user = searchController.getUserFromDatabase(b.getBidderUsername());
                removeChickenForBidFromUser(user, b);
                searchController.removeBidFromDatabase(b.getId());
                userController.updateUser(user);
            }
        }
        chicken.getBids().clear();
        chicken.getBids().add(bid);
        chicken.setChickenStatus(Chicken.ChickenStatus.BORROWED);
        chicken.setBorrowerUsername(bid.getBidderUsername());
        chicken = searchController.updateChickenInDatabase(chicken);

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
        ArrayList<Chicken> chickens = user.getMyChickens();
        for (Chicken c : chickens) {
            if (c.getBids().contains(bid)) {
                chickens.remove(c);
                break;
            }
        }

        UserController userController = ChickBidsApplication.getUserController();
        userController.updateUser(user);
    }

    public void putBidOnChicken(Bid bid, Chicken chicken) throws ChickenException {
        SearchController searchController = ChickBidsApplication.getSearchController();

        if (bid.getAmount() < getHighestBidForChicken(chicken)) {
            throw new ChickenException("Bid is not high enough");
        } else if ((chicken.getChickenStatus() == Chicken.ChickenStatus.NOT_AVAILABLE)
            || (chicken.getChickenStatus() == Chicken.ChickenStatus.BORROWED)) {
            throw new ChickenException("Bid cannot be placed on borrowed or not available chickens");
        }

        bid = searchController.addBidToDatabase(bid);
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        chicken.getBids().add(bid);
        chicken.setChickenStatus(Chicken.ChickenStatus.BIDDED);
        updateChickenForMe(chicken);
        current_user.addChicken(chicken);
        addNotificationForBid(bid);
    }

    public void updateBidForMyChicken(Bid bid) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        Chicken chicken;
        try {
            chicken = getChickenForBidForCurrentUser(bid);
        } catch (ChickenException e) {
        }
        //TODO: DO STUFF
        searchController.updateBidInDatabase(bid);
    }

    public void returnChickenToOwner(Chicken chicken) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        UserController userController = ChickBidsApplication.getUserController();

        for (Bid b : chicken.getBids()) {
            searchController.removeBidFromDatabase(b.getId());
        }

        chicken.getBids().clear();
        chicken.setChickenStatus(Chicken.ChickenStatus.AVAILABLE);
        chicken = searchController.updateChickenInDatabase(chicken);

        userController.updateUser(userController.getCurrentUser());
    }

    // Notifications
    public void addNotification(String username, String message) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        User user = searchController.getUserFromDatabase(username);

        Notification notification = new Notification(Notification.notificationMessageBuilderForMailbox(current_user.getUsername(), message));
        notification = searchController.addNotificationToDatabase(notification);

        user.addNotification(notification);
        searchController.updateUserInDatabase(user);
    }

    public void addNotificationForBid(Bid bid) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();

        String notificationMessage = Notification.notificationMessageBuilderForBid(bid);
        Notification notification = new Notification(notificationMessage);
        notification = searchController.addNotificationToDatabase(notification);
        current_user.addNotification(notification);
    }

    public void dismissNotification(Notification notification) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();

        current_user.getNotifications().remove(notification);
        searchController.removeNotificationFromDatabase(notification.getId());
    }

    public void dismissAllNotifications() {
        ArrayList<Notification> notifications = (ArrayList) ChickBidsApplication.getUserController().getCurrentUser().getNotifications().clone();
        for (Notification n : notifications) {
            dismissNotification(n);
        }
    }

    public boolean userHasNotifications() {
        User current_user = ChickBidsApplication.getUserController().getCurrentUser();
        return !current_user.getNotifications().isEmpty();
    }

    public void popupNotificationToast(Context context) {
        Toast toast = Toast.makeText(context, R.string.pending_notifications, Toast.LENGTH_LONG);
        if (userHasNotifications())
            toast.show();
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

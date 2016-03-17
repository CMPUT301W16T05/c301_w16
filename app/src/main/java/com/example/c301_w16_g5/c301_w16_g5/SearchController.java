package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * <code>SearchController</code> handles calls to objects and methods inside the
 * ElasticSearchBackend class.
 *
 * @author  Alex
 * @version 1.4, 03/02/2016
 * @see     Chicken
 * @see     User
 * @see     Notification
 * @see     Bid
 * @see     ElasticSearchBackend
 */
public class SearchController {

    public ArrayList<Chicken> searchByKeyword(String keyword) {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();

        ElasticSearchBackend.SearchChickenTask searchTask = new ElasticSearchBackend.SearchChickenTask();
        searchTask.execute(keyword);
        try {
            chickens = searchTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return chickens;
    }

    /* these two methods are no longer necessary
    public ArrayList<Chicken> getChickensBorrowedByUser(User user) {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();


        ElasticSearchBackend.GetChickensBorrowedByUserTask getChickensTask
                = new ElasticSearchBackend.GetChickensBorrowedByUserTask();
        getChickensTask.execute(user);
        try {
            chickens = getChickensTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return chickens;
    }

    public ArrayList<Chicken> getChickensBidOnByUser(User user) {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();

        ElasticSearchBackend.GetChickensBidOnByUserTask getChickensTask
                = new ElasticSearchBackend.GetChickensBidOnByUserTask();
        getChickensTask.execute(user);
        try {
            chickens = getChickensTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return chickens;
    }
    */

    public Chicken addChickenToDatabase(Chicken chicken) {
        AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.AddChickenTask();
        executable.execute(chicken);
        Chicken chicken2 = new Chicken();
        try {
            chicken2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return chicken2;
    }

    public Chicken updateChickenInDatabase(Chicken chicken) {
        AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.UpdateChickenTask();
        executable.execute(chicken);
        Chicken chicken2 = new Chicken();
        try {
            chicken2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return chicken2;
    }

    public Chicken getChickenFromDatabase(String id) {
        ElasticSearchBackend.GetChickenByIdTask getChickenTask = new ElasticSearchBackend.GetChickenByIdTask();
        getChickenTask.execute(id);
        Chicken chicken = new Chicken();
        try {
            chicken = getChickenTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return chicken;
    }

    public void removeChickenFromDatabase(String id) {
        ElasticSearchBackend.DeleteChickenTask deleteChickenTask = new ElasticSearchBackend.DeleteChickenTask();
        deleteChickenTask.execute(id);
    }

    public void addUserToDatabase(User user) {
        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);
    }

    public void updateUserInDatabase(User user) {
        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);
    }

    public User getUserFromDatabase(String username) {
        User user =  null;

        ElasticSearchBackend.GetUserByUsernameTask getUserTask = new ElasticSearchBackend.GetUserByUsernameTask();
        getUserTask.execute(username);

        try {
            user = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void removeUserFromDatabase(String username) {
        ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
        deleteUserTask.execute(username);
    }

    public void changeUsernameInDatabase(User user, String oldUsername) {
        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);

        ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
        deleteUserTask.execute(oldUsername);
    }

    public Notification addNotificationToDatabase(Notification notification) {
        AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.AddNotificationTask();
        executable.execute(notification);
        Notification notification2 = new Notification("a");
        try {
            notification2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return notification2;
    }

    public Notification updateNotificationInDatabase(Notification notification) {
        AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.UpdateNotificationTask();
        executable.execute(notification);
        Notification notification2 = new Notification("a");
        try {
            notification2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return notification2;
    }

    public Notification getNotificationFromDatabase(String id) {
        ElasticSearchBackend.GetNotificationByIdTask getNotificationTask = new ElasticSearchBackend.GetNotificationByIdTask();
        getNotificationTask.execute(id);
        Notification notification = new Notification("a");
        try {
            notification = getNotificationTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return notification;
    }

    public void removeNotificationFromDatabase(String id) {
        ElasticSearchBackend.DeleteNotificationTask deleteNotificationTask = new ElasticSearchBackend.DeleteNotificationTask();
        deleteNotificationTask.execute(id);
    }

    public Bid addBidToDatabase(Bid bid) {
        AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.AddBidTask();
        executable.execute(bid);
        Bid bid2 = new Bid("a", "a", 0.00);
        try {
            bid2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bid2;
    }

    public Bid updateBidInDatabase(Bid bid) {
        AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.UpdateBidTask();
        executable.execute(bid);
        Bid bid2 = new Bid("a", "a", 0.00);
        try {
            bid2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bid2;
    }

    public Bid getBidFromDatabase(String id) {
        ElasticSearchBackend.GetBidByIdTask getBidTask = new ElasticSearchBackend.GetBidByIdTask();
        getBidTask.execute(id);
        Bid bid = new Bid("a", "a", 0.00);
        try {
            bid = getBidTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bid;
    }

    public void removeBidFromDatabase(String id) {
        ElasticSearchBackend.DeleteBidTask deleteBidTask = new ElasticSearchBackend.DeleteBidTask();
        deleteBidTask.execute(id);
    }

    public Location addLocationToDatabase(Location location) {
        AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.AddLocationTask();
        executable.execute(location);
        Location location1 = new Location(0.00, 0.00);
        try {
            location1 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return location1;
    }

    public Location updateLocationInDatabase(Location location) {
        AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.UpdateLocationTask();
        executable.execute(location);
        Location location1 = new Location(0.00, 0.00);
        try {
            location1 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return location1;
    }

    public Location getLocationFromDatabase(String id) {
        ElasticSearchBackend.GetLocationByIdTask getLocationTask = new ElasticSearchBackend.GetLocationByIdTask();
        getLocationTask.execute(id);
        Location location = new Location(0.00, 0.00);
        try {
            location = getLocationTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return location;
    }

    public void removeLocationFromDatabase(String id) {
        ElasticSearchBackend.DeleteLocationTask deleteLocationTask = new ElasticSearchBackend.DeleteLocationTask();
        deleteLocationTask.execute(id);
    }
}

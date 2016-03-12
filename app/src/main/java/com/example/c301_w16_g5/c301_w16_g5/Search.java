package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by athompso on 3/11/16.
 */
public class Search {

    public ArrayList<Chicken> searchByKeyword(String keyword) {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();
        return chickens;
    }

    public Chicken addChickenToDatabase(Chicken chicken) {
        AsyncTask<Chicken, Void, Chicken> executable = new SearchController.AddChickenTask();
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
        AsyncTask<Chicken, Void, Chicken> executable = new SearchController.UpdateChickenTask();
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
        SearchController.GetChickenByIdTask getChickenTask = new SearchController.GetChickenByIdTask();
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
        SearchController.DeleteChickenTask deleteChickenTask = new SearchController.DeleteChickenTask();
        deleteChickenTask.execute(id);
    }

    public void addUserToDatabase(User user) {
        AsyncTask<User, Void, Void> executable = new SearchController.AddUserTask();
        executable.execute(user);
    }

    public void updateUserInDatabase(User user) {
        AsyncTask<User, Void, Void> executable = new SearchController.AddUserTask();
        executable.execute(user);
    }

    public User getUserFromDatabase(String username) {
        User user = new User("a","a","a","a","a","a");

        SearchController.GetUserByUsernameTask getUserTask = new SearchController.GetUserByUsernameTask();
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
        SearchController.DeleteUserTask deleteUserTask = new SearchController.DeleteUserTask();
        deleteUserTask.execute(username);
    }

    public Notification addNotificationToDatabase(Notification notification) {
        AsyncTask<Notification, Void, Notification> executable = new SearchController.AddNotificationTask();
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
        AsyncTask<Notification, Void, Notification> executable = new SearchController.UpdateNotificationTask();
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
        SearchController.GetNotificationByIdTask getNotificationTask = new SearchController.GetNotificationByIdTask();
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
        SearchController.DeleteNotificationTask deleteNotificationTask = new SearchController.DeleteNotificationTask();
        deleteNotificationTask.execute(id);
    }

    public Bid addBidToDatabase(Bid bid) {
        AsyncTask<Bid, Void, Bid> executable = new SearchController.AddBidTask();
        executable.execute(bid);
        Bid bid2 = new Bid("a", 0.00);
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
        AsyncTask<Bid, Void, Bid> executable = new SearchController.UpdateBidTask();
        executable.execute(bid);
        Bid bid2 = new Bid("a", 0.00);
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
        SearchController.GetBidByIdTask getBidTask = new SearchController.GetBidByIdTask();
        getBidTask.execute(id);
        Bid bid = new Bid("a", 0.00);
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
        SearchController.DeleteBidTask deleteBidTask = new SearchController.DeleteBidTask();
        deleteBidTask.execute(id);
    }
}

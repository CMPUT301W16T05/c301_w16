package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by athompso on 3/6/16.
 */
public class ElasticSearchBackendTest extends ActivityInstrumentationTestCase2 {
    public ElasticSearchBackendTest() {
        super(SearchesActivity.class);
    }

    public void testAddChickenTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com",
                "5875555555", "Loads");
        Chicken chicken = new Chicken("Bob", "Very friendly", user.getUsername());

        AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.AddChickenTask();
        executable.execute(chicken);
    }

    public void testDeleteChickenTask() {
        ElasticSearchBackend.DeleteChickenTask deleteChickenTask = new ElasticSearchBackend.DeleteChickenTask();
        deleteChickenTask.execute("yTQftaIcRuGJ_hBC3l09tg");
    }

    public void testDeleteBidTask() {
        ElasticSearchBackend.DeleteBidTask deleteBidTask = new ElasticSearchBackend.DeleteBidTask();
        deleteBidTask.execute("3uQtrnPmRfqu9QxKKahRrA");
    }

    public void testAddUserTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com",
                "5875555555", "Not Much");
        Chicken chicken = new Chicken("Bob", "Very friendly", user.getUsername());
        user.addChicken(chicken);

        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);
    }

    public void testGetUserByUsernameTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com", "5875555555", "Not Much");
        Chicken chicken = new Chicken("Bob", "Very friendly", "jsmith");
        Notification notification = new Notification("Urgent");

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
        user.addChicken(chicken2);

        AsyncTask<Notification, Void, Notification> executable1 = new ElasticSearchBackend.AddNotificationTask();
        executable1.execute(notification);
        Notification notification2 = new Notification("a");
        try {
            notification2 = executable1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        user.addNotification(notification2);

        AsyncTask<User, Void, Void> executable2 = new ElasticSearchBackend.AddUserTask();
        executable2.execute(user);

        User user2 = new User("a","a","a","a","a","a");

        ElasticSearchBackend.GetUserByUsernameTask getUserTask = new ElasticSearchBackend.GetUserByUsernameTask();
        getUserTask.execute("jsmith");

        try {
            user2 = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(user2.getUsername(),"jsmith");
        assertEquals(user2.getFirstName(),"John");
        assertEquals(user2.getLastName(),"Smith");
        assertEquals(user2.getEmail(),"jsmith@gmail.com");
        assertEquals(user2.getPhoneNumber(),"5875555555");
        assertEquals(user2.getChickenExperience(),"Not Much");

        assertEquals(user2.getChicken(0).getName(),chicken.getName());
        assertEquals(user2.getChicken(0).getDescription(),chicken.getDescription());
        assertEquals(user2.getChicken(0).getChickenStatus(),chicken.getChickenStatus());
        assertEquals(user2.getChicken(0).getOwnerUsername(),chicken.getOwnerUsername());

        assertEquals(user2.getNotifications().get(0).getMessage(),notification.getMessage());
    }

    public void testGetChickenByIdTask() {
        Chicken chicken1 = new Chicken("Bob", "Very friendly", "jsmith");
        Chicken chicken2 = new Chicken();
        Chicken chicken3 = new Chicken();
        Bid bid = new Bid("jsmith", "b", 10.00);

        AsyncTask<Bid, Void, Bid> executable1 = new ElasticSearchBackend.AddBidTask();
        executable1.execute(bid);
        Bid bid2 = new Bid("a", "a", 0.00);
        try {
            bid2 = executable1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        chicken1.getBids().add(bid2);

        AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.AddChickenTask();
        executable.execute(chicken1);
        try {
            chicken3 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchBackend.GetChickenByIdTask getChickenTask = new ElasticSearchBackend.GetChickenByIdTask();
        getChickenTask.execute(chicken3.getId());
        try {
            chicken2 = getChickenTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(chicken1.getName(), chicken2.getName());
        assertEquals(chicken1.getDescription(), chicken2.getDescription());
        assertEquals(chicken1.getChickenStatus(), chicken2.getChickenStatus());
        assertEquals(chicken1.getOwnerUsername(), chicken2.getOwnerUsername());

        assertEquals(chicken1.getBids().get(0).getBidderUsername(), bid.getBidderUsername());
        assertEquals(chicken1.getBids().get(0).getAmount(), bid.getAmount());
        assertEquals(chicken1.getBids().get(0).getBidStatus(), bid.getBidStatus());
    }

    public void testGetNotificationByIdTask() {
        Notification notification1 = new Notification("test");
        Notification notification2 = new Notification("a");
        Notification notification3 = new Notification("a");

        AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.AddNotificationTask();
        executable.execute(notification1);

        try {
             notification2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchBackend.GetNotificationByIdTask getNotificationTask = new ElasticSearchBackend.GetNotificationByIdTask();
        getNotificationTask.execute(notification2.getId());
        try {
            notification3 = getNotificationTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(notification1.getMessage(), notification3.getMessage());
        assertEquals(notification1.getDate(), notification2.getDate());
    }

    public void testGetBidByIdTask() {
        Bid bid1 = new Bid("jsmith", "b", 10.00);
        Bid bid2 = new Bid("a", "a", 0.00);
        Bid bid3 = new Bid("a", "a", 0.00);

        AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.AddBidTask();
        executable.execute(bid1);

        try {
            bid2 = executable.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchBackend.GetBidByIdTask getBidTask = new ElasticSearchBackend.GetBidByIdTask();
        getBidTask.execute(bid2.getId());
        try {
            bid3 = getBidTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(bid1.getBidderUsername(),bid3.getBidderUsername());
        assertEquals(bid1.getAmount(),bid3.getAmount());
        assertEquals(bid1.getBidStatus(),bid3.getBidStatus());
    }

    public void testSearchChickenTask() {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();

        ElasticSearchBackend.SearchChickenTask searchTask = new ElasticSearchBackend.SearchChickenTask();
        searchTask.execute("Bob");
        try {
            chickens = searchTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertTrue(chickens.size() != 0);
    }
}

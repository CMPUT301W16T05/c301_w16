package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
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
    private ArrayList<Chicken> offlineChickens;

    /* Offline Behaviour */
    public SearchController() {
        offlineChickens = new ArrayList<>();
    }

    private void saveOfflineChickens() {
        try {
            FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput("chickens.sav", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.offlineChickens, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void loadOfflineChickens() {
        try {
            FileInputStream fis = ChickBidsApplication.getApp().openFileInput("chickens.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<Chicken>>() {}.getType();
            this.offlineChickens = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            Log.i("ERROR", "Chickens were not loaded from file");
        }
    }

    public void saveUserOffline(User user) {
        try {
            FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput(user.getUsername() + ".sav", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public User loadUserOffline(String username) {
        User user = null;

        try {
            FileInputStream fis = ChickBidsApplication.getApp().openFileInput(username + ".sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            user = gson.fromJson(in, User.class);

        } catch (FileNotFoundException e) {
            Log.i("ERROR", "User was not loaded from file");
        }

        return user;
    }

    /**
     * Updates the database with offline chicken push requests.
     *
     * @return  list of chickens pushed to database
     */
    public ArrayList<Chicken> pushOfflineChickensToDatabase() {
        ArrayList<Chicken> chickens = new ArrayList<>();

        if (checkOnline()) {
            loadOfflineChickens();
            for (Chicken chicken : offlineChickens) {
                Chicken chicken1 = addChickenToDatabase(chicken);
                chickens.add(chicken1);
            }

            offlineChickens.clear();
            saveOfflineChickens();
        }

        return chickens;
    }

    /* Searching */

    /**
     * Finds the chickens matching given keyword criteria.
     *
     * @return  list of all chickens whose info contains the keyword
     */
    public ArrayList<Chicken> searchByKeyword(String keyword) {
        ArrayList<Chicken> chickens = new ArrayList<>();

        if (checkOnline()) {
            ElasticSearchBackend.SearchChickenTask searchTask = new ElasticSearchBackend.SearchChickenTask();
            searchTask.execute(keyword);
            try {
                chickens = searchTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chickens;
    }

    /* Chickens */

    /**
     * Gets all saved chickens from the Elasticsearch database.
     *
     * @return  list of all chickens
     */
    public ArrayList<Chicken> getAllChickens() {
        ArrayList<Chicken> chickens = new ArrayList<>();

        if (checkOnline()) {
            ElasticSearchBackend.GetAllChickensTask searchTask = new ElasticSearchBackend.GetAllChickensTask();
            searchTask.execute("");
            try {
                chickens = searchTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chickens;
    }

    /**
     * Saves the given chicken as a new chicken in the Elasticsearch database.
     *
     * @param   chicken the chicken to add to the database
     * @return  the saved chicken
     */
    public Chicken addChickenToDatabase(Chicken chicken) {
        Chicken chicken2 = null;

        if (checkOnline()) {
            AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.AddChickenTask();
            executable.execute(chicken);
            try {
                chicken2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            loadOfflineChickens();
            offlineChickens.add(chicken);
            saveOfflineChickens();
            chicken2 = chicken;
        }

        return chicken2;
    }

    /**
     * Updates the given existing chicken in the Elasticsearch database.
     *
     * @param   chicken the chicken to update in the database
     * @return  the updated chicken
     */
    public Chicken updateChickenInDatabase(Chicken chicken) {
        Chicken chicken2 = null;

        if (checkOnline()) {
            AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.UpdateChickenTask();
            executable.execute(chicken);
            try {
                chicken2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chicken2;
    }

    /**
     * Retrieves the chicken with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows chickens to be saved and
     * retrieved.
     *
     * @param   id  the id of the chicken to find
     * @return  the matching chicken
     */
    public Chicken getChickenFromDatabase(String id) {
        Chicken chicken = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetChickenByIdTask getChickenTask = new ElasticSearchBackend.GetChickenByIdTask();
            getChickenTask.execute(id);
            try {
                chicken = getChickenTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chicken;
    }

    /**
     * Deletes the chicken with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows chickens to be saved and
     * retrieved.
     *
     * @param   id  the id of the chicken to delete
     */
    public void removeChickenFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteChickenTask deleteChickenTask = new ElasticSearchBackend.DeleteChickenTask();
            deleteChickenTask.execute(id);
        }
    }

    /* Users */

    /**
     * Saves the given user as a new user in the Elasticsearch database.
     *
     * @param   user    the user to add to the database
     */
    public void addUserToDatabase(User user) {
        saveUserOffline(user);

        if (checkOnline()) {
            AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
            executable.execute(user);
        }
    }

    /**
     * Updates the given existing user in the Elasticsearch database.
     *
     * @param   user    the user to update in the database
     */
    public void updateUserInDatabase(User user) {
        saveUserOffline(user);

        if (checkOnline()) {
            AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
            executable.execute(user);
        }
    }

    /**
     * Retrieves the user with the given username from the Elasticsearch
     * database.  Username is a unique identifier of users.
     *
     * @param   username    the username of the user to find
     * @return  the matching user
     */
    public User getUserFromDatabase(String username) {
        User user =  null;

        if (checkOnline()) {
            ElasticSearchBackend.GetUserByUsernameTask getUserTask = new ElasticSearchBackend.GetUserByUsernameTask();
            getUserTask.execute(username);

            try {
                user = getUserTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        } else {
            user = loadUserOffline(username);
        }

        return user;
    }

    /**
     * Deletes the user with the given username from the Elasticsearch
     * database.  Username is a unique identifier of users.
     *
     * @param   username    the username of the user to delete
     */
    public void removeUserFromDatabase(String username) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
            deleteUserTask.execute(username);
        }
    }

    /**
     * Updates the given existing user's username in the Elasticsearch database.
     *
     * @param   user    the user to update in the database
     * @param   oldUsername the user's previous username, currently saved
     */
    public void changeUsernameInDatabase(User user, String oldUsername) {
        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);

        ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
        deleteUserTask.execute(oldUsername);
    }

    /* Notifications */

    /**
     * Saves the given notification in the Elasticsearch database.
     *
     * @param   notification    the notification to save
     */
    public Notification addNotificationToDatabase(Notification notification) {
        Notification notification2 = null;

        if (checkOnline()) {
            AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.AddNotificationTask();
            executable.execute(notification);
            try {
                notification2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification2;
    }

    /**
     * Updates the given existing notification in the Elasticsearch database.
     *
     * @param   notification    the notification to update in the database
     * @return  the updated notification
     */
    public Notification updateNotificationInDatabase(Notification notification) {
        Notification notification2 = null;

        if (checkOnline()) {
            AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.UpdateNotificationTask();
            executable.execute(notification);
            try {
                notification2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification2;
    }

    /**
     * Retrieves the notification with the given ID from the Elasticsearch
     * database.  ID is a unique identifier which allows notifications to be
     * saved and retrieved.
     *
     * @param   id  the id of the notification to find
     * @return  the matching notification
     */
    public Notification getNotificationFromDatabase(String id) {
        Notification notification = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetNotificationByIdTask getNotificationTask = new ElasticSearchBackend.GetNotificationByIdTask();
            getNotificationTask.execute(id);
            try {
                notification = getNotificationTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification;
    }

    /**
     * Deletes the notification with the given ID from the Elasticsearch
     * database.  ID is a unique identifier which allows notifications to be
     * saved and retrieved.
     *
     * @param   id  the id of the notification to delete
     */
    public void removeNotificationFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteNotificationTask deleteNotificationTask = new ElasticSearchBackend.DeleteNotificationTask();
            deleteNotificationTask.execute(id);
        }
    }

    /* Bids */

    /**
     * Saves the given bid in the Elasticsearch database.
     *
     * @param   bid the bid to save
     */
    public Bid addBidToDatabase(Bid bid) {
        Bid bid2 = null;

        if (checkOnline()) {
            AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.AddBidTask();
            executable.execute(bid);
            try {
                bid2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid2;
    }

    /**
     * Updates the given existing bid in the Elasticsearch database.
     *
     * @param   bid the bid to update in the database
     * @return  the updated bid
     */
    public Bid updateBidInDatabase(Bid bid) {
        Bid bid2 = null;

        if (checkOnline()) {
            AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.UpdateBidTask();
            executable.execute(bid);
            try {
                bid2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid2;
    }

    /**
     * Retrieves the bid with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows bids to be saved and
     * retrieved.
     *
     * @param   id  the id of the bid to find
     * @return  the matching bid
     */
    public Bid getBidFromDatabase(String id) {
        Bid bid = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetBidByIdTask getBidTask = new ElasticSearchBackend.GetBidByIdTask();
            getBidTask.execute(id);
            try {
                bid = getBidTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid;
    }

    /**
     * Deletes the bid with the given ID from the Elasticsearch database.  ID
     * is a unique identifier which allows bids to be saved and retrieved.
     *
     * @param   id  the id of the bid to delete
     */
    public void removeBidFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteBidTask deleteBidTask = new ElasticSearchBackend.DeleteBidTask();
            deleteBidTask.execute(id);
        }
    }

    /* Location */

    /**
     * Saves the given location in the Elasticsearch database.
     *
     * @param   location    the location to save
     */
    public Location addLocationToDatabase(Location location) {
        Location location1 = null;

        if (checkOnline()) {
            AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.AddLocationTask();
            executable.execute(location);
            try {
                location1 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location1;
    }

    /**
     * Updates the given existing location in the Elasticsearch database.
     *
     * @param   location    the location to update in the database
     * @return  the updated location
     */
    public Location updateLocationInDatabase(Location location) {
        Location location1 = null;

        if (checkOnline()) {
            AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.UpdateLocationTask();
            executable.execute(location);
            try {
                location1 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location1;
    }

    /**
     * Retrieves the location with the given ID from the Elasticsearch
     * database.  ID is a unique identifier which allows locations to be saved
     * and retrieved.
     *
     * @param   id  the id of the location to find
     * @return  the matching location
     */
    public Location getLocationFromDatabase(String id) {
        Location location = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetLocationByIdTask getLocationTask = new ElasticSearchBackend.GetLocationByIdTask();
            getLocationTask.execute(id);
            try {
                location = getLocationTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location;
    }

    /**
     * Deletes the location with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows locations to be saved and retrieved.
     *
     * @param   id  the id of the location to delete
     */
    public void removeLocationFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteLocationTask deleteLocationTask = new ElasticSearchBackend.DeleteLocationTask();
            deleteLocationTask.execute(id);
        }
    }

    /* Letter */

    /**
     * Saves the given letter in the Elasticsearch database.
     *
     * @param   letter  the letter to save
     */
    public Letter addLetterToDatabase(Letter letter) {
        Letter letter2 = null;

        if (checkOnline()) {
            AsyncTask<Letter, Void, Letter> executable = new ElasticSearchBackend.AddLetterTask();
            executable.execute(letter);
            try {
                letter2 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter2;
    }

    /**
     * Updates the given existing letter in the Elasticsearch database.
     *
     * @param   letter  the letter to update in the database
     * @return  the updated letter
     */
    public Letter updateLetterInDatabase(Letter letter) {
        Letter letter1 = null;

        if (checkOnline()) {
            AsyncTask<Letter, Void, Letter> executable = new ElasticSearchBackend.UpdateLetterTask();
            executable.execute(letter);
            try {
                letter1 = executable.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter1;
    }

    /**
     * Retrieves the letter with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows letters to be saved and
     * retrieved.
     *
     * @param   id  the id of the letter to find
     * @return  the matching letter
     */
    public Letter getLetterFromDatabase(String id) {
        Letter letter = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetLetterByIdTask getLetterTask = new ElasticSearchBackend.GetLetterByIdTask();
            getLetterTask.execute(id);
            try {
                letter = getLetterTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter;
    }

    /**
     * Deletes the letter with the given ID from the Elasticsearch database.
     * ID is a unique identifier which allows letters to be saved and
     * retrieved.
     *
     * @param   id  the id of the letter to delete
     */
    public void removeLetterFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteLetterTask deleteLetterTask = new ElasticSearchBackend.DeleteLetterTask();
            deleteLetterTask.execute(id);
        }
    }

    /**
     * Retrieves the letters of the given user from the Elasticsearch database.
     *
     * @param   user    the user whose letters are retrieved
     * @return  the list of all letters of that user
     */
    public ArrayList<Letter> getLettersForUser(User user) {
        ArrayList<Letter> letters = new ArrayList<>();

        if (checkOnline()) {
            ElasticSearchBackend.GetLettersForUserTask getLettersTask
                    = new ElasticSearchBackend.GetLettersForUserTask();
            getLettersTask.execute(user);
            try {
                letters = getLettersTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letters;
    }

    /**
     * Checks if a device is connected to the internet.
     *
     * @return  true if connected, false otherwise
     */
    public boolean checkOnline() {
        // Taken from http://stackoverflow.com/questions/9570237/android-check-internet-connection
        // Answer by Seshu Vinay
        // Accessed by athompson0 on March 29 2016
        ConnectivityManager cm = (ConnectivityManager) ChickBidsApplication.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

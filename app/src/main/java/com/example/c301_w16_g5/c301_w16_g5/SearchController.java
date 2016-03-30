package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public SearchController() {
        offlineChickens = new ArrayList<Chicken>();
    }

    private void saveOfflineChickens() {
        try {
            FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput("chickens.sav", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.offlineChickens, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
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
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public ArrayList<Chicken> pushOfflineChickensToDatabase() {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();

        if (checkOnline()) {
            loadOfflineChickens();
            for (Chicken chicken : offlineChickens) {
                Chicken chicken1 = addChickenToDatabase(chicken);
                chickens.add(chicken1);
            }
        }

        return chickens;
    }

    public ArrayList<Chicken> searchByKeyword(String keyword) {
        ArrayList<Chicken> chickens = new ArrayList<Chicken>();

        if (checkOnline()) {
            ElasticSearchBackend.SearchChickenTask searchTask = new ElasticSearchBackend.SearchChickenTask();
            searchTask.execute(keyword);
            try {
                chickens = searchTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chickens;
    }

    public Chicken addChickenToDatabase(Chicken chicken) {
        Chicken chicken2 = null;

        if (checkOnline()) {
            AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.AddChickenTask();
            executable.execute(chicken);
            try {
                chicken2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
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

    public Chicken updateChickenInDatabase(Chicken chicken) {
        Chicken chicken2 = null;

        if (checkOnline()) {
            AsyncTask<Chicken, Void, Chicken> executable = new ElasticSearchBackend.UpdateChickenTask();
            executable.execute(chicken);
            try {
                chicken2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chicken2;
    }

    public Chicken getChickenFromDatabase(String id) {
        Chicken chicken = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetChickenByIdTask getChickenTask = new ElasticSearchBackend.GetChickenByIdTask();
            getChickenTask.execute(id);
            try {
                chicken = getChickenTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return chicken;
    }

    public void removeChickenFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteChickenTask deleteChickenTask = new ElasticSearchBackend.DeleteChickenTask();
            deleteChickenTask.execute(id);
        }
    }

    public void addUserToDatabase(User user) {
        try {
            FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput(user.getUsername() + ".sav", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        if (checkOnline()) {
            AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
            executable.execute(user);
        }
    }

    public void updateUserInDatabase(User user) {
        try {
            FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput(user.getUsername() + ".sav", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        if (checkOnline()) {
            AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
            executable.execute(user);
        }
    }

    public User getUserFromDatabase(String username) {
        User user =  null;

        if (checkOnline()) {
            ElasticSearchBackend.GetUserByUsernameTask getUserTask = new ElasticSearchBackend.GetUserByUsernameTask();
            getUserTask.execute(username);

            try {
                user = getUserTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            try {
                FileOutputStream fos = ChickBidsApplication.getApp().openFileOutput(user.getUsername() + ".sav", 0);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
                Gson gson = new Gson();
                gson.toJson(user, out);
                out.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }

        } else {
            try {
                FileInputStream fis = ChickBidsApplication.getApp().openFileInput(username + ".sav");
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();

                // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
                Type userType = new TypeToken<User>() {
                }.getType();
                user = gson.fromJson(in, userType);

            } catch (FileNotFoundException e) {
                Log.i("ERROR", "User was not loaded from file");
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return user;
    }

    public void removeUserFromDatabase(String username) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
            deleteUserTask.execute(username);
        }
    }

    public void changeUsernameInDatabase(User user, String oldUsername) {
        AsyncTask<User, Void, Void> executable = new ElasticSearchBackend.AddUserTask();
        executable.execute(user);

        ElasticSearchBackend.DeleteUserTask deleteUserTask = new ElasticSearchBackend.DeleteUserTask();
        deleteUserTask.execute(oldUsername);
    }

    public Notification addNotificationToDatabase(Notification notification) {
        Notification notification2 = null;

        if (checkOnline()) {
            AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.AddNotificationTask();
            executable.execute(notification);
            try {
                notification2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification2;
    }

    public Notification updateNotificationInDatabase(Notification notification) {
        Notification notification2 = null;

        if (checkOnline()) {
            AsyncTask<Notification, Void, Notification> executable = new ElasticSearchBackend.UpdateNotificationTask();
            executable.execute(notification);
            try {
                notification2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification2;
    }

    public Notification getNotificationFromDatabase(String id) {
        Notification notification = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetNotificationByIdTask getNotificationTask = new ElasticSearchBackend.GetNotificationByIdTask();
            getNotificationTask.execute(id);
            try {
                notification = getNotificationTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return notification;
    }

    public void removeNotificationFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteNotificationTask deleteNotificationTask = new ElasticSearchBackend.DeleteNotificationTask();
            deleteNotificationTask.execute(id);
        }
    }

    public Bid addBidToDatabase(Bid bid) {
        Bid bid2 = null;

        if (checkOnline()) {
            AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.AddBidTask();
            executable.execute(bid);
            try {
                bid2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid2;
    }

    public Bid updateBidInDatabase(Bid bid) {
        Bid bid2 = null;

        if (checkOnline()) {
            AsyncTask<Bid, Void, Bid> executable = new ElasticSearchBackend.UpdateBidTask();
            executable.execute(bid);
            try {
                bid2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid2;
    }

    public Bid getBidFromDatabase(String id) {
        Bid bid = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetBidByIdTask getBidTask = new ElasticSearchBackend.GetBidByIdTask();
            getBidTask.execute(id);
            try {
                bid = getBidTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bid;
    }

    public void removeBidFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteBidTask deleteBidTask = new ElasticSearchBackend.DeleteBidTask();
            deleteBidTask.execute(id);
        }
    }

    public Location addLocationToDatabase(Location location) {
        Location location1 = null;

        if (checkOnline()) {
            AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.AddLocationTask();
            executable.execute(location);
            try {
                location1 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location1;
    }

    public Location updateLocationInDatabase(Location location) {
        Location location1 = null;

        if (checkOnline()) {
            AsyncTask<Location, Void, Location> executable = new ElasticSearchBackend.UpdateLocationTask();
            executable.execute(location);
            try {
                location1 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location1;
    }

    public Location getLocationFromDatabase(String id) {
        Location location = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetLocationByIdTask getLocationTask = new ElasticSearchBackend.GetLocationByIdTask();
            getLocationTask.execute(id);
            try {
                location = getLocationTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return location;
    }

    public void removeLocationFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteLocationTask deleteLocationTask = new ElasticSearchBackend.DeleteLocationTask();
            deleteLocationTask.execute(id);
        }
    }

    public Letter addLetterToDatabase(Letter letter) {
        Letter letter2 = null;

        if (checkOnline()) {
            AsyncTask<Letter, Void, Letter> executable = new ElasticSearchBackend.AddLetterTask();
            executable.execute(letter);
            try {
                letter2 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter2;
    }

    public Letter updateLetterInDatabase(Letter letter) {
        Letter letter1 = null;

        if (checkOnline()) {
            AsyncTask<Letter, Void, Letter> executable = new ElasticSearchBackend.UpdateLetterTask();
            executable.execute(letter);
            try {
                letter1 = executable.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter1;
    }

    public Letter getLetterFromDatabase(String id) {
        Letter letter = null;

        if (checkOnline()) {
            ElasticSearchBackend.GetLetterByIdTask getLetterTask = new ElasticSearchBackend.GetLetterByIdTask();
            getLetterTask.execute(id);
            try {
                letter = getLetterTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letter;
    }

    public void removeLetterFromDatabase(String id) {
        if (checkOnline()) {
            ElasticSearchBackend.DeleteLetterTask deleteLetterTask = new ElasticSearchBackend.DeleteLetterTask();
            deleteLetterTask.execute(id);
        }
    }

    public ArrayList<Letter> getLettersForUser(User user) {
        ArrayList<Letter> letters = new ArrayList<Letter>();

        if (checkOnline()) {
            ElasticSearchBackend.GetLettersForUserTask getLettersTask
                    = new ElasticSearchBackend.GetLettersForUserTask();
            getLettersTask.execute(user);
            try {
                letters = getLettersTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return letters;
    }

    public boolean checkOnline() {
        //Taken from http://stackoverflow.com/questions/9570237/android-check-internet-connection
        //Answer by Seshu Vinay
        //Accessed by athompson0 on March 29 2016
        ConnectivityManager cm = (ConnectivityManager) ChickBidsApplication.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

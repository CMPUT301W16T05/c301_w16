package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by shahzeb on 03/03/16.
 */
public class UserController {
    // Singleton
    transient private static User user = null;

    public User getUser(String username) {
        AsyncTask<String, Void, User> task = new SearchController.GetUserByUsernameTask();

        task.execute(username);

        try {
            this.user = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void updateUser(User user) {

    }

    public User getCurrentUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

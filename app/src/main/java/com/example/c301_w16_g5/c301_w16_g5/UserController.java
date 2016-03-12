package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shahzeb on 03/03/16.
 */
public class UserController {
    // Singleton
    transient private static User user = null;

    public User getUser(String username) {
        AsyncTask<String, Void, User> task = new ElasticSearchBackend.GetUserByUsernameTask();

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

    // Validate User
    public static boolean genericValidateLettersNumbersOnly(String input) {
        //http://stackoverflow.com/questions/8923398/regex-doesnt-work-in-string-matches
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(input);
        return !m.find();
    }

    public static boolean validateUsername(String username) {
        // TODO: ensure username is not already taken
        return genericValidateLettersNumbersOnly(username);
    }

    public static boolean validateNames(String name) {
        return genericValidateLettersNumbersOnly(name);
    }

    public static boolean validateEmail(String email) {
        return email.matches(".+\\@.+\\..+");
    }

    public static boolean validatePhoneNumber(String number) {
        return number.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    }

    public static boolean validateChickenExperience(String experience) {
        return experience.length() > 0;
    }
}

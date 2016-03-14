package com.example.c301_w16_g5.c301_w16_g5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for operating on the <code>User</code> model to
 * perform complicated actions.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     EditProfileActivity
 * @see     DisplayProfileActivity
 */
public class UserController {
    // Singleton
    transient private static User user = null;

    public User getUser(String username) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        return searchController.getUserFromDatabase(username);
    }

    public void updateUser(User user) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        searchController.updateUserInDatabase(user);
        setUser(user);
    }

    public void saveUser(User user) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        searchController.addUserToDatabase(user);
        setUser(user);
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
        return genericValidateLettersNumbersOnly(username) && username.length() > 0;
    }

    public static boolean usernameInUse(String username) {
        return ChickBidsApplication.getUserController().getUser(username) != null &&
                !ChickBidsApplication.getUserController().getCurrentUser().getUsername().equals(username);
    }

    public static boolean validateNames(String name) {
        return genericValidateLettersNumbersOnly(name) && name.length() > 0;
    }

    public static boolean validateEmail(String email) {
        return email.matches(".+\\@.+\\..+");
    }

    public static boolean validatePhoneNumber(String number) {
        return number.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}") || number.matches("[0-9]{10}");
    }

    public static boolean validateChickenExperience(String experience) {
        return experience.length() > 0;
    }
}

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
    /* Singleton */
    transient private static User user = null;

    public User getUser(String username) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        return searchController.getUserFromDatabase(username);
    }

    /**
     * Updates the information of an existing user in the Elasticsearch database.
     *
     * @param   user    the user to update
     */
    public void updateUser(User user) {
        SearchController searchController = ChickBidsApplication.getSearchController();
        searchController.updateUserInDatabase(user);
        setUser(user);
    }

    /**
     * Saves a new user to the Elasticsearch database.
     *
     * @param   user    the user to add
     */
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

    /* Validate User */

    public static boolean genericValidateLettersNumbersOnly(String input) {
        //http://stackoverflow.com/questions/8923398/regex-doesnt-work-in-string-matches
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(input);
        return !m.find();
    }

    /**
     * Checks that a username is strictly alphanumeric.
     *
     * @param   username    the username to check
     * @return  true if valid username, false otherwise
     */
    public static boolean validateUsername(String username) {
        return genericValidateLettersNumbersOnly(username) && username.length() > 0;
    }

    /**
     * Checks whether a username is already in use by another user
     *
     * @param   username    the username to check
     * @return  true if username take, false if username available
     */
    public static boolean usernameInUse(String username) {
        return ChickBidsApplication.getUserController().getUser(username) != null &&
                !ChickBidsApplication.getUserController().getCurrentUser().getUsername().equals(username);
    }

    /**
     * Checks that a user's first or last name is valid.
     *
     * @param   name    the name to check
     * @return  true if valid name, false otherwise
     */
    public static boolean validateNames(String name) {
        return genericValidateLettersNumbersOnly(name) && name.length() > 0;
    }

    /**
     * Checks that an email address follows the typical email address format.
     *
     * @param   email    the email address to check
     * @return  true if valid email address, false otherwise
     */
    public static boolean validateEmail(String email) {
        return email.matches(".+\\@.+\\..+");
    }

    /**
     * Checks that a phone number follows the valid format.  Valid phone
     * numbers have 10 digits, and are optionally hyphenated.
     *
     * @param   number    the phone number to check
     * @return  true if valid phone number, false otherwise
     */
    public static boolean validatePhoneNumber(String number) {
        return number.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}") || number.matches("[0-9]{10}");
    }

    /**
     * Checks that a chicken experience description is valid.
     *
     * @param   experience  the experience description to check
     * @return  true if valid experience description, false otherwise
     */
    public static boolean validateChickenExperience(String experience) {
        return experience.length() > 0;
    }
}

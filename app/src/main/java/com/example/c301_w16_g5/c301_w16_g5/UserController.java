package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by shahzeb on 03/03/16.
 */
public class UserController {
    // TODO: do we just want a single user for an instance of the application?
    // Singleton
    transient private static User user = null;

    public User getUser(String username) {
        SearchController searchController = ChickBidsApplication.getSearchController();

        try {
            user = searchController.getUserFromServer(username);
        } catch (SearchException e) {
            user = searchController.putUsernameOnServer(username);
        }

        return user;
    }

    public static User getCurrentUser() {
        return user;
    }


}

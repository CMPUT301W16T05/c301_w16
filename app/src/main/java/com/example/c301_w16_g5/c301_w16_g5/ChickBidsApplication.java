package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Application;

/**
 * Created by Hailey on 2016-02-26.
 */
public class ChickBidsApplication extends Application {
    transient private static ChickenController chickenController = null;
    transient private static SearchController searchController = null;
    transient private static UserController userController = null;

    public static ChickenController getChickenController() {
        if (chickenController == null) {
            chickenController = new ChickenController();
        }
        return chickenController;
    }

    public static SearchController getSearchController() {
        if (searchController == null) {
            searchController = new SearchController();
        }
        return searchController;
    }

    public static UserController getUserController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }
}

package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Application;
import android.os.Bundle;

/**
 * <code>ChickBidsApplication</code> is the primary application class.  It
 * manages the singleton controllers in this MVC model.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     ChickenController
 * @see     ElasticSearchBackend
 * @see     UserController
 */
public class ChickBidsApplication extends Application {
    transient private static ChickenController chickenController = null;
    transient private static SearchController searchController = null;
    transient private static UserController userController = null;
    private static ChickBidsApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }

    public static ChickBidsApplication getApp() {
        return app;
    }

    /**
     * Access to the Chicken Controller Singleton
     *
     * @return the chicken controller
     */
    public static ChickenController getChickenController() {
        if (chickenController == null) {
            chickenController = new ChickenController();
        }
        return chickenController;
    }

    /**
     * Access to the Search Controller Singleton
     *
     * @return the search controller
     */
    public static SearchController getSearchController() {
        if (searchController == null) {
            searchController = new SearchController();
        }
        return searchController;
    }

    /**
     * Access to the User Controller Singleton
     *
     * @return the user controller
     */
    public static UserController getUserController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }
}

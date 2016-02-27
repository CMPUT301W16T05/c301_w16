package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Application;

/**
 * Created by Hailey on 2016-02-26.
 */
public class ChickBidsApplication extends Application {
    // TODO: do we just want a single user for an instance of the application?
    // Singleton
    transient private static User user = null;
    static User getUser() {
        if (user == null) {
            // TODO: Question: Do we need to load stuff from file to have persistence instead?
//            user = new User();
        }
        return user;
    }
}

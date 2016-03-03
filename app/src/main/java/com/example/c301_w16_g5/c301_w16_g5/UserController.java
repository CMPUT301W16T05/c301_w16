package com.example.c301_w16_g5.c301_w16_g5;

/**
 * Created by shahzeb on 03/03/16.
 */
public class UserController {
    // TODO: do we just want a single user for an instance of the application?
    // Singleton
    transient private static User user = null;

    public static User getUser() {
        if (user == null) {
            // TODO: Question: Do we need to load stuff from file to have persistence instead?
//            user = new User();
        }
        return user;
    }
}

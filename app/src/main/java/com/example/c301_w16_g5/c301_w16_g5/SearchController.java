package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * This class will be the anomaly compared to the other controllers.
 * This will be used directly from models and also from views.
 * E.g.Chicken will eventually use this to fill its local ArrayList on
 *  initializing.
 */
public class SearchController {
    public User getUserFromServer(String username) throws SearchException {
        return null;
    }

    public User putUsernameOnServer(String username) {
        return null;
    }

    public ArrayList<Chicken> getAllChickensForUser(User user) {
        // Return all chickens from server that are in users possession or
        // owned by user.
        // This includes:
        // - Owned by user
        // - Borrowed by user
        return null;
    }
}

package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * This class is responsible for operating on the Chicken model
 * to perform complicated actions.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     AddChickenActivity
 * @see     ItemChickenActivity
 * @see     EditProfileActivity
 */
public class ChickenController {
    public ArrayList<Chicken> getAllChickensInPossession() {
        User user = UserController.getUser();
        return user
    }

    // chicken lending management
    public ArrayList<Chicken> getBorrowedFromOthers() {
        // TODO: implement
        return null;
    }

    public ArrayList<Chicken> getBorrowedFromMe() {
        // TODO: implement
        return null;
    }

    public ArrayList<Chicken> getChickensWithBids() {
        // TODO: implement
        return null;
    }



}

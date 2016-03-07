package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * This class is responsible for operating on the <code>Chicken</code> model to
 * perform complicated actions.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     AddChickenActivity
 * @see     EditChickenActivity
 * @see     OwnerChickenProfileActivity
 * @see     BorrowerChickenProfileActivity
 */
public class ChickenController {
    public ChickenController() {
    }

    public ArrayList<Chicken> getAllChickensInPossession() {
        return UserController.getCurrentUser().getMyChickens();
    }

    // chicken lending management
    public ArrayList<Chicken> getBorrowedFromOthers() {
        ArrayList<Chicken> all_chickens = getAllChickensInPossession();
        ArrayList<Chicken> borrowed_chickens = new ArrayList<Chicken>();
        for (Chicken chicken : all_chickens) {
            //TODO: fill this. Possibly need to add equals on User.
        }
        return borrowed_chickens;
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

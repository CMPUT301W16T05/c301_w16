package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by shahzeb on 2/10/16.
 */
public class User {
    private UserProfile userProfile;

    public static User getCurrentUser() {
        return null;
    }

    public void removeAllChickens() {
    }

    public void addChicken(Chicken chicken) {
    }

    public long getNumberOfThings() {
        return 0;
    }

    public Chicken getChicken(int i) {
        return null;
    }

    public void deleteChicken(Chicken chicken) {
    }

    public ArrayList<Chicken> getBorrowedFromOthers(ArrayList<Chicken> listOfThings) {
        return null;
    }

    public ArrayList<Chicken> getBorrowedFromMe(ArrayList<Chicken> listOfThings) {
        return null;
    }

    public void releaseChicken(Chicken chicken){

    }

    public Chicken getChicken(Chicken chicken){
        return null;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean hasUserProfile() {
        return userProfile != null;
    }
}


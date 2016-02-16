package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by shahzeb on 2/10/16.
 */
public class User {
    private UserProfile userProfile;
    private double balance;
    private ArrayList<Chicken> myChickens;
    private ArrayList<Bid> bidsOnMyChickens;
    private ArrayList<Notification> myNotifications;

    public static User getCurrentUser() {
        return null;
    }

    public void removeAllChickens() {
    }

    public void addChicken(Chicken chicken) {
    }

    public long getNumberOfChickens() {
        return 0;
    }

    public Chicken getChicken(int index) {
        return null;
    }

    public boolean hasChicken(Chicken chicken){
        return false;
    }

    public void deleteChicken(Chicken chicken) {
    }

    public ArrayList<Chicken> getChickensBorrowed() {
        return null;
    }

    public ArrayList<Chicken> getChickensLent() {
        return null;
    }

    public void releaseChicken(Chicken chicken){

    }

    public void acceptBid(Bid bid) {

    }

    public void declineBid(Bid bid) {

    }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Chicken> getChickensWithBids() {
        return null;
    }

    public ArrayList<Bid> getBids() {
        return bidsOnMyChickens;
    }

    public ArrayList<Notification> getNotifications() {
        return myNotifications;
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


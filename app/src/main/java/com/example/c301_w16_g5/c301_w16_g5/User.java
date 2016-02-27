package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

/**
 * Created by shahzeb on 2/10/16.
 */
public class User extends GenericModel<GenericView> {
    // profile info
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String chickenExperience;

    private double balance;
    private ArrayList<Chicken> myChickens;
    private ArrayList<Notification> myNotifications;

    // TODO: constructor? Or no, because using singleton?

    // TODO: delete this, because ChickBidsApplication will deal w/ it?
    public static User getCurrentUser() {
        return null;
    }

    // profile management
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getChickenExperience() {
        return chickenExperience;
    }

    public void setChickenExperience(String chickenExperience) {
        this.chickenExperience = chickenExperience;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // basic chicken management
    public void addChicken(Chicken chicken) {
        myChickens.add(chicken);
    }

    public long getNumberOfChickens() {
        return myChickens.size();
    }

    public Chicken getChicken(int index) {
        return myChickens.get(index);
    }

    public boolean hasChicken(Chicken chicken){
        return myChickens.contains(chicken);
    }

    public void deleteChicken(Chicken chicken) {
        myChickens.remove(chicken);
    }

    public void removeAllChickens() {
        myChickens = new ArrayList<Chicken>();
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

    public ArrayList<Notification> getNotifications() {
        // TODO: implement
        return myNotifications;
    }
}


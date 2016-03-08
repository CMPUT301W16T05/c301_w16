package com.example.c301_w16_g5.c301_w16_g5;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * A <code>User</code> is an operator of the application.  Interactions between
 * users include lending/borrowing chickens, and viewing other users' profiles.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     UserProfileActivity
 * @see     AddProfileActivity
 * @see     EditProfileActivity
 * @see     UserController
 */
public class User extends GenericModel<GenericView> {
    // profile info
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String chickenExperience;

    // TODO: Remove this and store on username instead.
    @JestId
    protected String id;

    // My chickens should contain all chickens owned by
    // user OR currently in user's posession, i.e. borrowed chickens
    private ArrayList<Chicken> myChickens;
    private ArrayList<Notification> myNotifications;

    /**
     * For new users.
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param chickenExperience
     */
    public User(String username, String firstName, String lastName, String email, String phoneNumber, String chickenExperience) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.chickenExperience = chickenExperience;
        this.myChickens = new ArrayList<Chicken>();
        this.myNotifications = new ArrayList<Notification>();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<Chicken> getMyChickens() {
        return myChickens;
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

    public ArrayList<Notification> getNotifications() {
        // TODO: implement
        return myNotifications;
    }
}


package com.example.c301_w16_g5.c301_w16_g5;

public class UserProfile {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String chickenExperience;

    public UserProfile(String username, String firstName, String lastName,
                       String email, String phoneNumber,
                       String chickenExperience) {
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setChickenExperience(chickenExperience);
    }

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
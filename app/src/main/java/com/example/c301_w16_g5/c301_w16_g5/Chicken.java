package com.example.c301_w16_g5.c301_w16_g5;

/**
 * Created by Robin on 2016-02-10.
 */
public class Chicken {
    public enum Status {
        AVAILABLE,
        BORROWED,
        NOT_AVAILABLE
    }

    private String name;
    private Status status;
    private User owner;
    private String description;
    private double rate;

    public Chicken() {
    }

    public Chicken(String name, String description, double rate, Status status) {
        this.name = name;
        this.owner = User.getCurrentUser();
        this.description = description;
        this.rate = rate;
        this.status = status;
    }

    public void update(String name, String description, double rate, Status status) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return owner;
    }

    public void setUser(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
package com.example.c301_w16_g5.c301_w16_g5;

/**
 * This exception class deals with cases where an invalid operation is
 * performed upon a <code>Chicken</code>.
 *
 * @author  Hailey
 * @version 1.4, 03/12/2016
 * @see     User
 * @see     EditProfileActivity
 */
public class UserException extends Exception {
    public UserException(String detailMessage) {
        super(detailMessage);
    }
}
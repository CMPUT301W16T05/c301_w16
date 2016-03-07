package com.example.c301_w16_g5.c301_w16_g5;

/**
 * This exception class deals with cases where an invalid operation is
 * performed upon a <code>Chicken</code>.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     Chicken
 * @see     AddChickenActivity
 * @see     ChickenController
 */
public class ChickenException extends Exception {
    public ChickenException(String detailMessage) {
        super(detailMessage);
    }
}

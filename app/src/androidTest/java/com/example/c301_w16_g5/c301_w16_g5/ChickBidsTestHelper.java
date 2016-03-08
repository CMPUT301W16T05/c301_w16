package com.example.c301_w16_g5.c301_w16_g5;

/**
 * Created by shahzeb on 3/8/16.
 */
public class ChickBidsTestHelper {
    public static String username_1 = "username_1";
    public static String username_2 = "username_2";
    public static String username_3 = "username_3";
    public static String username_4 = "username_4";

    public static String first_name_1 = "Bob";
    public static String first_name_2 = "Tim";
    public static String first_name_3 = "Jill";
    public static String first_name_4 = "Sam";

    public static String last_name_1 = "Rains";
    public static String last_name_2 = "Wilt";
    public static String last_name_3 = "Nod";
    public static String last_name_4 = "Olo";

    public static String chicken_name_1 = "Nelson";
    public static String chicken_name_2 = "Bart";
    public static String chicken_name_3 = "Ralph";
    public static String chicken_name_4 = "Apu";
    public static String chicken_name_5 = "Moe";
    public static String chicken_name_6 = "Delicious";

    public static String email_1 = "wooho@hotmail.com";
    public static String email_2 = "thisisreal@gmail.com";
    public static String email_3 = "aliensarereal@hotmail.com";
    public static String email_4 = "dontemailme@yahoo.com";

    public static String phone_1 = "7801113333";
    public static String phone_2 = "6145552222";
    public static String phone_3 = "9942220000";
    public static String phone_4 = "4556223566";

    public static String user_experience_1 = "A lot";
    public static String user_experience_2 = "Not much";
    public static String user_experience_3 = "So much";
    public static String user_experience_4 = "Much";

    public static String description_1 = "This is a description";
    public static String description_2 = "This chicken can fly";
    public static String description_3 = "This chicken can talk";
    public static String description_4 = "This chicken can act";

    private static ChickenController chickenController = new ChickenController();

    public static User genericUser1() {
        return new User(username_1, first_name_1, last_name_1, email_1, phone_1, user_experience_1);
    }

    public static User genericUser2() {
        return new User(username_2, first_name_2, last_name_2, email_2, phone_2, user_experience_2);
    }

    public static User genericUser3() {
        return new User(username_3, first_name_3, last_name_3, email_3, phone_3, user_experience_3);
    }

    public static User genericUser4() {
        return new User(username_4, first_name_4, last_name_4, email_4, phone_4, user_experience_4);
    }

    public static Chicken genericChicken1_AvailUser1() {
        return new Chicken(chicken_name_1, description_1, Chicken.ChickenStatus.AVAILABLE, username_1);
    }

    public static Chicken genericChicken2_BidUser2() {
        return new Chicken(chicken_name_2, description_2, Chicken.ChickenStatus.BIDDED, username_2);
    }

    public static Chicken genericChicken3_AvailUser3() {
        return new Chicken(chicken_name_3, description_3, Chicken.ChickenStatus.AVAILABLE, username_3);
    }

    public static Chicken genericChicken4_BorrUser4() {
        return new Chicken(chicken_name_4, description_4, Chicken.ChickenStatus.BORROWED, username_4);
    }

    public static Chicken genericChicken5_BorrUser1() {
        return new Chicken(chicken_name_5, description_1, Chicken.ChickenStatus.BORROWED, username_1);
    }

    public static Chicken genericChicken6_BidUser1() {
        return new Chicken(chicken_name_6, description_2, Chicken.ChickenStatus.BIDDED, username_1);
    }

    public static ChickenController getChickenController() {
        return chickenController;
    }
}

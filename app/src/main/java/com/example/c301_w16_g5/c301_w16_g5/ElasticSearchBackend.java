package com.example.c301_w16_g5.c301_w16_g5;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * <code>ElasticSearchBackend</code> is responsible for all calls to elasticsearch, including
 * adding, updating, getting, and removing Chickens, Users, Notifications and Bids from the
 * database. Keyword and term-specific searches are also handled here.
 *
 * verifyClient() method was originally written by earthiverse for the Lab 7 materials. Other
 * methods are altered and expanded versions of the Lab 7 lonelytwitter elasticsearch methods.
 *
 * @author  Alex
 * @version 1.4, 03/02/2016
 * @see     Chicken
 * @see     User
 * @see     Notification
 * @see     Bid
 * @see     SearchController
 */
public class ElasticSearchBackend {
    private static JestDroidClient client;

    public static class SearchChickenTask extends AsyncTask<String, Void, ArrayList<Chicken>> {
        @Override
        protected ArrayList<Chicken> doInBackground(String... searches) {
            verifyClient();
            String keyword = searches[0];
            ArrayList<Chicken> chickens = new ArrayList<Chicken>();

            String query = "{ \"query\" : { \"query_string\" : { \"query\" : \"" + keyword + "\" } } }";
            Search search = new Search.Builder(query).addIndex("c301w16t05").addType("chicken").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<String> chickenStrings = result.getSourceAsStringList();
                    for (String chickenString : chickenStrings) {
                        Chicken chicken = parseChicken(chickenString);
                        chickens.add(chicken);
                    }
                } else {
                    Log.i("INFO","Search failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chickens;
        }
    }

    /* these two methods are no longer necessary
    public static class GetChickensBorrowedByUserTask extends AsyncTask<User, Void, ArrayList<Chicken>> {
        @Override
        protected ArrayList<Chicken> doInBackground(User... users) {
            verifyClient();
            User user = users[0];
            ArrayList<Chicken> chickens = new ArrayList<Chicken>();

            String query = "{ \"query\" : { \"match\" : { \"borrower\" : \"" + user.getUsername()
                     + "\" } } }";

            Search search = new Search.Builder(query).addIndex("c301w16t05").addType("chicken").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<String> chickenStrings = result.getSourceAsStringList();
                    for (String chickenString : chickenStrings) {
                        Chicken chicken = parseChicken(chickenString);
                        chickens.add(chicken);
                    }
                } else {
                    Log.i("INFO","Search failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chickens;
        }
    }

    public static class GetChickensBidOnByUserTask extends AsyncTask<User, Void, ArrayList<Chicken>> {
        @Override
        protected ArrayList<Chicken> doInBackground(User... users) {
            verifyClient();
            User user = users[0];
            ArrayList<Chicken> chickens = new ArrayList<Chicken>();

            String query = "{ \"query\" : { \"match\" : { \"bidder\" : \"" + user.getUsername()
                    + "\" } } }";

            Search search = new Search.Builder(query).addIndex("c301w16t05").addType("bid").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<String> bidStrings = result.getSourceAsStringList();
                    for (String bidString : bidStrings) {
                        Bid bid = parseBid(bidString);

                        Get get = new Get.Builder("c301w16t05", bid.getChickenId()).type("chicken").build();
                        Chicken chicken = new Chicken();
                        try {
                            JestResult result2 = client.execute(get);
                            if (result2.isSucceeded()) {
                                chicken = parseChicken(result.getSourceAsString());
                            } else {
                                Log.i("INFO","Getting the chicken failed");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        chickens.add(chicken);

                    }
                } else {
                    Log.i("INFO","Search failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chickens;
        }
    }
    */

    public static class AddChickenTask extends AsyncTask<Chicken, Void, Chicken> {
        @Override
        protected Chicken doInBackground(Chicken... chickens) {
            verifyClient();
            Chicken chicken = chickens[0];
            Map<String, String> source = formatChicken(chicken);

            Index index = new Index.Builder(source).index("c301w16t05").type("chicken").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    chicken.setId(result.getId());
                } else {
                    Log.i("INFO","Adding a chicken failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chicken;
        }
    }

    public static class UpdateChickenTask extends AsyncTask<Chicken, Void, Chicken> {
        @Override
        protected Chicken doInBackground(Chicken... chickens) {
            verifyClient();
            Chicken chicken = chickens[0];
            Map<String, String> source = formatChicken(chicken);

            Index index = new Index.Builder(source).index("c301w16t05").type("chicken")
                    .id(chicken.getId()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    chicken.setId(result.getId());
                } else {
                    Log.i("INFO","Updating chicken failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chicken;
        }
    }

    public static class GetChickenByIdTask extends AsyncTask<String, Void, Chicken> {
        @Override
        protected Chicken doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            Chicken chicken = new Chicken();

            Get get = new Get.Builder("c301w16t05", id).type("chicken").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    chicken = parseChicken(result.getSourceAsString());
                    chicken.setId(id);
                } else {
                    Log.i("INFO","Getting the chicken failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chicken;
        }
    }

    public static class DeleteChickenTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];

            Delete delete = new Delete.Builder(id).index("c301w16t05").type("chicken").build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            User user = users[0];
            Map<String, String> source = formatUser(user);

            Index index = new Index.Builder(source).index("c301w16t05").type("user")
                    .id(user.getUsername()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                } else {
                    Log.i("INFO","Adding a user failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class GetUserByUsernameTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            User user = new User("a","a","a","a","a","a");

            Get get = new Get.Builder("c301w16t05", id).type("user").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    user = parseUser(result.getSourceAsString());
                } else {
                    user = null;
                    Log.i("INFO","Getting the user failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user;
        }
    }

    public static class DeleteUserTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... usernames) {
            verifyClient();
            String username = usernames[0];

            Delete delete = new Delete.Builder(username).index("c301w16t05").type("user").build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class AddBidTask extends AsyncTask<Bid, Void, Bid> {
        @Override
        protected Bid doInBackground(Bid... bids) {
            verifyClient();
            Bid bid = bids[0];
            Map<String, String> source = formatBid(bid);

            Index index = new Index.Builder(source).index("c301w16t05").type("bid").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    bid.setId(result.getId());
                } else {
                    Log.i("INFO","Adding a bid failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bid;
        }
    }

    public static class UpdateBidTask extends AsyncTask<Bid, Void, Bid> {
        @Override
        protected Bid doInBackground(Bid... bids) {
            verifyClient();
            Bid bid = bids[0];
            Map<String, String> source = formatBid(bid);

            Index index = new Index.Builder(source).index("c301w16t05").type("bid").id(bid.getId())
                    .build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                } else {
                    Log.i("INFO","Updating bid failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bid;
        }
    }

    public static class GetBidByIdTask extends AsyncTask<String, Void, Bid> {
        @Override
        protected Bid doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            Bid bid = new Bid("a","a",1.0);

            Get get = new Get.Builder("c301w16t05", id).type("bid").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    bid = parseBid(result.getSourceAsString());
                    bid.setId(id);
                } else {
                    Log.i("INFO","Getting the bid failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bid;
        }
    }

    public static class DeleteBidTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];

            Delete delete = new Delete.Builder(id).index("c301w16t05").type("bid").build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class AddNotificationTask extends AsyncTask<Notification, Void, Notification> {
        @Override
        protected Notification doInBackground(Notification... notifications) {
            verifyClient();
            Notification notification = notifications[0];
            Map<String, String> source = formatNotification(notification);

            Index index = new Index.Builder(source).index("c301w16t05").type("notification").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    notification.setId(result.getId());
                } else {
                    Log.i("INFO","Adding a notification failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return notification;
        }
    }

    public static class UpdateNotificationTask extends AsyncTask<Notification, Void, Notification> {
        @Override
        protected Notification doInBackground(Notification... notifications) {
            verifyClient();
            Notification notification = notifications[0];
            Map<String, String> source = formatNotification(notification);

            Index index = new Index.Builder(source).index("c301w16t05").type("notification")
                    .id(notification.getId()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                } else {
                    Log.i("INFO","Updating notification failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return notification;
        }
    }

    public static class GetNotificationByIdTask extends AsyncTask<String, Void, Notification> {
        @Override
        protected Notification doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            Notification notification = new Notification("a");

            Get get = new Get.Builder("c301w16t05", id).type("notification").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    notification = parseNotification(result.getSourceAsString());
                    notification.setId(id);
                } else {
                    Log.i("INFO","Getting the notification failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return notification;
        }
    }

    public static class DeleteNotificationTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];

            Delete delete = new Delete.Builder(id).index("c301w16t05").type("notification").build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class AddLocationTask extends AsyncTask<Location, Void, Location> {
        @Override
        protected Location doInBackground(Location... locations) {
            verifyClient();
            Location location = locations[0];
            Map<String, String> source = formatLocation(location);

            Index index = new Index.Builder(source).index("c301w16t05").type("location").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    location.setId(result.getId());
                } else {
                    Log.i("INFO","Adding a location failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return location;
        }
    }

    public static class UpdateLocationTask extends AsyncTask<Location, Void, Location> {
        @Override
        protected Location doInBackground(Location... locations) {
            verifyClient();
            Location location = locations[0];
            Map<String, String> source = formatLocation(location);

            Index index = new Index.Builder(source).index("c301w16t05").type("location")
                    .id(location.getId()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                } else {
                    Log.i("INFO","Updating location failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return location;
        }
    }

    public static class GetLocationByIdTask extends AsyncTask<String, Void, Location> {
        @Override
        protected Location doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            Location location = new Location(0.00, 0.00);

            Get get = new Get.Builder("c301w16t05", id).type("location").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    location = parseLocation(result.getSourceAsString());
                    location.setId(id);
                } else {
                    Log.i("INFO","Getting the location failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return location;
        }
    }

    public static class DeleteLocationTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];

            Delete delete = new Delete.Builder(id).index("c301w16t05").type("location").build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static Map<String, String> formatChicken(Chicken chicken) {
        Map<String, String> source = new LinkedHashMap<>();

        source.put("name", chicken.getName());
        source.put("description", chicken.getDescription());
        source.put("chickenStatus", chicken.getChickenStatus().toString());
        source.put("owner", chicken.getOwnerUsername());

        if (chicken.getBorrowerUsername() == null) {
            source.put("borrower", "none");
        } else {
            source.put("borrower", chicken.getBorrowerUsername());
        }

        if (chicken.getBids().isEmpty()) {
            source.put("bids", "none");
        } else {
            String bids = "";
            for (Bid bid : chicken.getBids()) {
                bids = bids + bid.getId() + ",";
            }
            source.put("bids", bids);
        }

        if (chicken.getPicture() == null) {
            source.put("photo", "none");
        } else {
            source.put("photo",chicken.getPicture().toString());
        }

        return source;
    }

    public static Chicken parseChicken(String source) {
        String[] attrList = source.split("\"");
        Chicken chicken = new Chicken(attrList[3],attrList[7],attrList[15]);
        chicken.setChickenStatus(Chicken.ChickenStatus.valueOf(attrList[11]));

        if (!attrList[19].equals("none")) {
            chicken.setBorrowerUsername(attrList[19]);
        }

        if (!attrList[23].equals("none")) {
            String[] bids = attrList[23].split(",");
            for (String chickenId : bids) {
                Get get = new Get.Builder("c301w16t05", chickenId).type("bid").build();
                try {
                    JestResult result = client.execute(get);
                    if (result.isSucceeded()) {
                        Bid bid = parseBid(result.getSourceAsString());
                        chicken.getBids().add(bid);
                    } else {
                        Log.i("TODO","Getting the chicken failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!attrList[27].equals("none")) {
            chicken.setPicture(Uri.parse(attrList[27]));
        }

        return chicken;
    }

    public static Map<String, String> formatUser(User user) {
        Map<String, String> source = new LinkedHashMap<>();

        source.put("username", user.getUsername());
        source.put("firstName", user.getFirstName());
        source.put("lastName", user.getLastName());
        source.put("email", user.getEmail());
        source.put("phoneNumber", user.getPhoneNumber());
        source.put("chickenExperience", user.getChickenExperience());

        if (user.getMyChickens().isEmpty()) {
            source.put("myChickens", "none");
        } else {
            String chickens = "";
            for (Chicken chicken: user.getMyChickens()) {
                chickens = chickens + chicken.getId() + ",";
            }
            source.put("myChickens", chickens);
        }

        if (user.getNotifications().isEmpty()) {
            source.put("notifications", "none");
        } else {
            String notifications = "";
            for (Notification notification : user.getNotifications()) {
                notifications = notifications + notification.getId() + ",";
            }
            source.put("notifications", notifications);
        }

        return source;
    }

    public static User parseUser(String source) {
        String[] attrList = source.split("\"");
        User user = new User(attrList[3],attrList[7],attrList[11],attrList[15],attrList[19],attrList[23]);

        if (!attrList[27].equals("none")) {
            String[] chickens = attrList[27].split(",");
            for (String chickenId : chickens) {
                Get get = new Get.Builder("c301w16t05", chickenId).type("chicken").build();
                try {
                    JestResult result = client.execute(get);
                    if (result.isSucceeded()) {
                        Chicken chicken = parseChicken(result.getSourceAsString());
                        user.addChicken(chicken);
                    } else {
                        Log.i("TODO","Getting the chicken failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!attrList[31].equals("none")) {
            String[] notifications = attrList[31].split(",");
            for (String notifId : notifications) {
                Get get = new Get.Builder("c301w16t05", notifId).type("notification").build();
                try {
                    JestResult result = client.execute(get);
                    if (result.isSucceeded()) {
                        Notification notification = parseNotification(result.getSourceAsString());
                        user.addNotification(notification);
                    } else {
                        Log.i("TODO","Getting the bid failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    public static Map<String, String> formatBid(Bid bid) {
        Map<String, String> source = new LinkedHashMap<>();

        source.put("bidder", bid.getBidderUsername());
        source.put("chicken", bid.getChickenId());
        source.put("amount",String.valueOf(bid.getAmount()));
        source.put("bidStatus",bid.getBidStatus().toString());

        if (bid.getLocation() == null) {
            source.put("location", "none");
        } else {
            source.put("location", bid.getLocation().getId());
        }

        return source;
    }

    public static Bid parseBid(String source) {
        String[] attrList = source.split("\"");
        Bid bid = new Bid(attrList[3],attrList[7],Double.parseDouble(attrList[11]));

        bid.setBidStatus(Bid.BidStatus.valueOf(attrList[15]));

        if (!attrList[19].equals("none")) {
            Get get = new Get.Builder("c301w16t05", attrList[19]).type("location").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    Location location = parseLocation(result.getSourceAsString());
                    bid.setLocation(location);
                } else {
                    Log.i("TODO","Getting the location failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bid;
    }

    public static Map<String, String> formatNotification(Notification notification) {
        Map<String, String> source = new LinkedHashMap<>();
        source.put("message", notification.getMessage());

        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        source.put("date", sdf.format(notification.getDate()));

        return source;
    }

    public static Notification parseNotification(String source) {
        String[] attrList = source.split("\"");
        Notification notification = new Notification(attrList[3]);

        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            notification.setDate(sdf.parse(attrList[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return notification;
    }

    public static Map<String, String> formatLocation(Location location) {
        Map<String, String> source = new LinkedHashMap<>();
        source.put("latitude", String.valueOf(location.getLatitude()));
        source.put("longitude", String.valueOf(location.getLongitude()));

        return source;
    }

    public static Location parseLocation(String source) {
        String[] attrList = source.split("\"");
        Location location = new Location(Double.parseDouble(attrList[3]), Double.parseDouble(attrList[7]));

        return location;
    }

    public static void verifyClient() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

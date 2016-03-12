package com.example.c301_w16_g5.c301_w16_g5;

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
 * This class will be the anomaly compared to the other controllers.
 * This will be used directly from models and also from views.
 * E.g.Chicken will eventually use this to fill its local ArrayList on
 *  initializing.
 */
public class ElasticSearchBackend {
    private static JestDroidClient client;

    public static class SearchChickenTask extends AsyncTask<String, Void, ArrayList<Chicken>> {
        @Override
        protected ArrayList<Chicken> doInBackground(String... searches) {
            verifyClient();
            String keyword = searches[0];
            ArrayList<Chicken> chickens = new ArrayList<Chicken>();

            String query = "{ \"query\" : { \" query_string\" : { \"query\" : \"" + keyword +
                    "\" } } }";

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
                    Log.i("INFO","SearchController failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return chickens;
        }
    }

    // TODO: Problem with setting chicken ID
    // Since two tasks are simultaneous, sometimes the main code will run before the chicken ID is
    // set, so if the chicken ID is accessed to soon it is null. The current fix is to capture the
    // chicken returned by this method in a new chicken object, identical to the old one except it
    // has an ID set.
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
                    Log.i("TODO","Adding a chicken failed");
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
                    Log.i("TODO","Updating chicken failed");
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
                } else {
                    Log.i("TODO","Getting the chicken failed");
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
                    Log.i("TODO","Adding a user failed");
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
                    Log.i("TODO","Getting the user failed");
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
                    Log.i("TODO","Adding a bid failed");
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
                    Log.i("TODO","Updating bid failed");
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
            Bid bid = new Bid("a",1.0);

            Get get = new Get.Builder("c301w16t05", id).type("bid").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    bid = parseBid(result.getSourceAsString());
                } else {
                    Log.i("TODO","Getting the bid failed");
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
                    Log.i("TODO","Adding a notification failed");
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
                    Log.i("TODO","Updating notification failed");
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
                } else {
                    Log.i("TODO","Getting the notification failed");
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

    public static Map<String, String> formatChicken(Chicken chicken) {
        Map<String, String> source = new LinkedHashMap<>();

        // TODO: Figure out how you want to store bids and photographs in elasticsearch
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

        return chicken;
    }

    public static Map<String, String> formatUser(User user) {
        Map<String, String> source = new LinkedHashMap<>();

        // TODO: Figure out how you want to store notifications in elasticsearch
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

        // TODO: add location when we implement that
        source.put("bidder", bid.getBidderUsername());
        source.put("amount",String.valueOf(bid.getAmount()));
        source.put("bidStatus",bid.getBidStatus().toString());

        return source;
    }

    public static Bid parseBid(String source) {
        String[] attrList = source.split("\"");
        Bid bid = new Bid(attrList[3],Double.parseDouble(attrList[7]));

        bid.setBidStatus(Bid.BidStatus.valueOf(attrList[11]));
        return bid;
    }

    public static Map<String, String> formatNotification(Notification notification) {
        Map<String, String> source = new LinkedHashMap<>();
        source.put("message",notification.getMessage());

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

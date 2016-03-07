package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * This class will be the anomaly compared to the other controllers.
 * This will be used directly from models and also from views.
 * E.g.Chicken will eventually use this to fill its local ArrayList on
 *  initializing.
 */
public class SearchController {
    private static JestDroidClient client;

    public static class AddChickenTask extends AsyncTask<Chicken, Void, Void> {
        @Override
        protected Void doInBackground(Chicken... chickens) {
            verifyClient();
            Chicken chicken = chickens[0];
            Map<String, String> source = formatChicken(chicken);

            Index index = new Index.Builder(source).index("c301w16t05").type("chicken").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    chicken.setId(result.getId());
                    Log.i("INFO","Adding a chicken succeeded");
                } else {
                    Log.i("TODO","Adding a chicken failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class GetChickenByIdTask extends AsyncTask<String, Void, Chicken> {
        @Override
        protected Chicken doInBackground(String... ids) {
            verifyClient();
            String id = ids[0];
            Chicken chicken;

            Get get = new Get.Builder("c301w16t05", id).type("chicken").build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    chicken = result.getSourceAsObject(Chicken.class);
                } else {
                    chicken = new Chicken();
                    Log.i("TODO","Getting the chicken failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
                chicken = new Chicken();
            }

            return chicken;
        }
    }

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();
            User user = users[0];
            Map<String, String> source = formatUser(user);

            Index index = new Index.Builder(source).index("c301w16t05").type("user").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    user.setId(result.getId());
                    Log.i("INFO","Adding a user succeeded");
                } else {
                    Log.i("TODO","Adding a user failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static Map<String, String> formatChicken(Chicken chicken) {
        Map<String, String> source = new LinkedHashMap<>();

        // TODO: Figure out how you want to store bids and photographs in elasticsearch
        // TODO: Replace owner and borrower usernames with IDs
        source.put("name", chicken.getName());
        source.put("description", chicken.getDescription());
        source.put("chickenStatus", chicken.getChickenStatus().toString());

        if (chicken.getOwner() == null) {
            source.put("owner", "none");
        } else {
            source.put("owner", chicken.getOwner().getUsername());
        }

        if (chicken.getBorrower() == null) {
            source.put("borrower", "none");
        } else {
            source.put("borrower", chicken.getBorrower().getUsername());
        }

        return source;
    }

    public static Map<String, String> formatUser(User user) {
        Map<String, String> source = new LinkedHashMap<>();

        // TODO: Figure out how you want to store notifications in elasticsearch
        // TODO: Replace chicken names with chicken IDs
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
                chickens = chickens + chicken.getName() + ",";
            }
            source.put("myChickens", chickens);
        }

        return source;
    }

    public User getUserFromServer(String username) throws SearchException {
        return null;
    }

    public User putUsernameOnServer(String username) {
        return null;
    }

    public ArrayList<Chicken> getAllChickensForUser(User user) {
        // Return all chickens from server that are in users possession or
        // owned by user.
        // This includes:
        // - Owned by user
        // - Borrowed by user
        return null;
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

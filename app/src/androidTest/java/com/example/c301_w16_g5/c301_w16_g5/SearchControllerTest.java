package com.example.c301_w16_g5.c301_w16_g5;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by athompso on 3/6/16.
 */
public class SearchControllerTest extends ActivityInstrumentationTestCase2 {
    public SearchControllerTest() {
        super(SearchActivity.class);
    }

    public void testAddChickenTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com",
                "5875555555", "Loads");
        Chicken chicken = new Chicken("Bob", "Very friendly",
                Chicken.ChickenStatus.AVAILABLE, user.getUsername());

        AsyncTask<Chicken, Void, Void> executable = new SearchController.AddChickenTask();
        executable.execute(chicken);
    }

    public void testAddUserTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com",
                "5875555555", "Loads");

        AsyncTask<User, Void, Void> executable = new SearchController.AddUserTask();
        executable.execute(user);
    }

    public void testGetChickenByIdTask() {
        User user = new User("jsmith", "John", "Smith", "jsmith@gmail.com",
                "5875555555", "Loads");
        Chicken chicken1 = new Chicken("Bob", "Very friendly",
                Chicken.ChickenStatus.AVAILABLE, user.getUsername());
        Chicken chicken2 = new Chicken();

        AsyncTask<Chicken, Void, Void> executable = new SearchController.AddChickenTask();
        executable.execute(chicken1);

        SearchController.GetChickenByIdTask getChickenTask = new SearchController.GetChickenByIdTask();
        getChickenTask.execute(chicken1.getId());
        try {
            chicken2 = getChickenTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(chicken1.getName(), chicken2.getName());
        assertEquals(chicken1.getDescription(), chicken2.getDescription());
        assertEquals(chicken1.getChickenStatus(), chicken2.getChickenStatus());
        assertEquals(chicken1.getOwnerID(), chicken2.getOwnerID());

    }
}

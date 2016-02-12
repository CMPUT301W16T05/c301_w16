package com.example.c301_w16_g5.c301_w16_g5;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailey on 2016-02-11.
 */
public class OfflineBehaviourTest extends ActivityInstrumentationTestCase2 {
    public OfflineBehaviourTest() {
        super(Chicken.class); // TODO: make sure this is right
    }

    // US 08.01.01
    public void TestAddAThingOffline() {
        // not sure how to deal with the connection, so do this for now:
        Server server = new Server();
        server.disconnect();

        User user = new User();
        Chicken chicken = new Chicken("Bob", "Friendly chicken", 13.55, Chicken.Status.AVAILABLE);
        user.addChicken(chicken);
        assert(!user.hasChicken(chicken));

        server.reconnect();

        assert(user.hasChicken(chicken));

        // simulate poor connectivity
        server = new Server();
        server.disconnect();

        user = new User();
        chicken = new Chicken("Bob", "Friendly chicken", 13.55, Chicken.Status.AVAILABLE);
        user.addChicken(chicken);
        assert(!user.hasChicken(chicken));

        server.reconnect();
        server.disconnect();
        server.reconnect();

        assert(user.hasChicken(chicken));
    }

}

package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * The main screen, from which the user can access their profile or chickens,
 * or perform a search
 *
 * @author  Satyen
 * @version 1.4, 03/02/2016
 */
public class HomeActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(myToolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // disable going back to login screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}

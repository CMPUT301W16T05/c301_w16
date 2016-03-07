package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * <code>SearchActivity</code> provides the screen from which a user will
 * search for chickens that they may wish to bid on.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     SearchController
 * @see     Chicken
 * @see     User
 */
public class SearchActivity extends ChickBidActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

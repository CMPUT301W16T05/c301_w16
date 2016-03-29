package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This activity displays to a particular user all of the bids upon their
 * chickens.  It is from here that a user can choose to accept or reject a bid.
 *
 * @author  Hailey
 * @version 1.4, 03/02/2016
 * @see     Bid
 * @see     User
 */
public class BidsActivity extends ChickBidActivity {

    private BidAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.bidsListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Bid> bids = ChickBidsApplication.getChickenController().getCurrentChicken().getBids();
        adapter = new BidAdapter(this, bids);

        test(); // TODO: remove

        listView.setAdapter(adapter);
    }

    private void test() {
        ArrayList<Bid> bids = new ArrayList<Bid>();
        bids.add(new Bid("sakolkartest", "0", 5.00));
        bids.add(new Bid("sakolkartest2", "0", 25.50));
        bids.add(new Bid("sakolkartest3", "0", 15.25));
        adapter = new BidAdapter(this, bids);
    }
}

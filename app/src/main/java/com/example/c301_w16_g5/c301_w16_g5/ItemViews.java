package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 * sample comment
 */
public class ItemViews extends ChickBidActivity {

    private enum TabCategory {
        POSSESSION,
        OWNED,
        OWNED_WITH_BIDS,
        LENT,
        BORROWED,
        BIDS_PLACED
    }

    private TabLayout.Tab tab_possession;
    private TabLayout.Tab tab_owned;
    private TabLayout.Tab tab_owned_with_bids;
    private TabLayout.Tab tab_lent;
    private TabLayout.Tab tab_borrowed;
    private TabLayout.Tab tab_bids_placed;

    private ArrayList<Chicken> listOfChickens = new ArrayList<Chicken>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_views);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tab_possession = tabLayout.newTab().setText(R.string.item_profile_possession).setTag(TabCategory.POSSESSION);
        tab_owned = tabLayout.newTab().setText(R.string.item_profile_owned).setTag(TabCategory.OWNED);
        tab_owned_with_bids = tabLayout.newTab().setText(R.string.item_profile_owned_with_bids).setTag(TabCategory.OWNED_WITH_BIDS);
        tab_lent = tabLayout.newTab().setText(R.string.item_profile_lent).setTag(TabCategory.LENT);
        tab_borrowed = tabLayout.newTab().setText(R.string.item_profile_borrowed).setTag(TabCategory.BORROWED);
        tab_bids_placed = tabLayout.newTab().setText(R.string.item_profile_bids_placed).setTag(TabCategory.BIDS_PLACED);

        tabLayout.addTab(tab_possession);
        tabLayout.addTab(tab_owned);
        tabLayout.addTab(tab_owned_with_bids);
        tabLayout.addTab(tab_lent);
        tabLayout.addTab(tab_borrowed);
        tabLayout.addTab(tab_bids_placed);

        tabLayout.setOnTabSelectedListener(new ItemTabListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_chicken_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_views, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(TabCategory tabCategory) {
        ChickenController chickenController = ChickBidsApplication.getChickenController();

        switch (tabCategory) {
            case POSSESSION:
                listOfChickens = chickenController.getAllChickensInMyPossession();
                break;
            case OWNED:
                listOfChickens = chickenController.getMyOwnedChickens();
                break;
            case OWNED_WITH_BIDS:
                listOfChickens = chickenController.getMyChickensWithBids();
                break;
            case LENT:
                listOfChickens = chickenController.getChickensLentByMe();
                break;
            case BORROWED:
                listOfChickens = chickenController.getChickensBorrowedFromOthers();
                break;
            case BIDS_PLACED:
                listOfChickens = chickenController.getChickensBiddedOnByMe();
                break;
            default:
                break;
        }
    }

    private void addListViewToTab(TabLayout.Tab tab) {
        updateList((TabCategory) tab.getTag());
        updateListView();
    }

    private void updateListView() {

    }

    private void createListView() {

    }

    private void removeListView() {

    }

    public class ItemTabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            addListViewToTab(tab);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}

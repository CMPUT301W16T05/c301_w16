package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private ListView listView;

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

        updateForTab(tab_possession);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_chicken_fab);
        // TODO: Add chicken here
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
                listOfChickens.add(new Chicken("Bob", "chicken", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
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
                listOfChickens.add(new Chicken("Tim", "chicken also", ChickBidsApplication.getUserController().getCurrentUser().getUsername()));
                break;
            default:
                break;
        }
    }

    private void updateForTab(TabLayout.Tab tab) {
        updateList((TabCategory) tab.getTag());
        refreshTableTitle(tab.getText().toString());
        refreshTableView();
    }

    private void refreshTableTitle(String text) {
        TextView textView = (TextView) findViewById(R.id.chickenListTitle);
        textView.setText(text);
    }

    private void fillTableView() {
        for (Chicken chicken : listOfChickens) {
            addChickenToTable(chicken);
        }
    }

    private void addChickenToTable(Chicken chicken) {
        /*  1/19/16 - Aby Mathew
            http://stackoverflow.com/questions/18207470/adding-table-rows-dynamically-in-android
         */
        TableLayout tableLayout = (TableLayout) findViewById(R.id.chickenListTable);

        // Add new row.
        TableRow row = new TableRow(this);

        TextView date = new TextView(this);
        date.setTextSize(18);
        date.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        date.setText(chicken.getName());
        date.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.35f));

        TextView station = new TextView(this);
        station.setTextSize(18);
        station.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        station.setText(chicken.getOwnerUsername());
        station.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.40f));

        TextView cost = new TextView(this);
        cost.setTextSize(18);
        cost.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        cost.setText(chicken.getChickenStatus().toString());
        cost.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.25f));

        row.addView(date);
        row.addView(station);
        row.addView(cost);

        row.setPadding(4, 4, 4, 4);

        // Clicking on row will allow the user to edit.
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: load chicken profile
                launch_editChicken();
            }
        });

        tableLayout.addView(row);
    }

    private void refreshTableView() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.chickenListTable);
        tableLayout.removeViews(1, tableLayout.getChildCount()-1);
        fillTableView();
    }

    public class ItemTabListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            updateForTab(tab);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    public void launch_editChicken(){
        Intent intent = new Intent(this, ChickenProfileActivity.class);
        startActivity(intent);
    }
}

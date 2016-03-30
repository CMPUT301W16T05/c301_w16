package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public static int LAT_LON_REQUEST = 5732;

    private BidAdapter adapter;
    private ListView listView;

    private Bid accepted_bid;

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
        Collections.sort(bids, new Comparator<Bid>() {
            @Override
            public int compare(Bid lhs, Bid rhs) {
                // highest bid at top of arraylist
                return -1 * (Double.valueOf(lhs.getAmount())).compareTo(rhs.getAmount());
            }
        });
        adapter = new BidAdapter(this, bids);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void refreshView() {
        Chicken chicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        ArrayList<Bid> bids = chicken.getBids();

        adapter = new BidAdapter(this, bids);
        listView.setAdapter(adapter);
    }

    private void acceptBid(Bid bid) {
        accepted_bid = bid;

        ChickenController chickenController = ChickBidsApplication.getChickenController();
        startActivityForResult(new Intent(this, LocationActivity.class), LAT_LON_REQUEST);

        refreshView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ChickenController chickenController = ChickBidsApplication.getChickenController();

        if (requestCode == LAT_LON_REQUEST) {
            if (resultCode == LocationActivity.LAT_LON_SUCCESS_CODE) {
                LatLng latLng = data.getExtras().getParcelable(LocationActivity.LAT_LON_KEY);
                Location location = new Location(latLng.latitude, latLng.longitude);
                chickenController.addLocationForBid(accepted_bid, location);
                try {
                    ChickBidsApplication.getChickenController().acceptBidForChicken(accepted_bid);
                } catch (ChickenException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class BidAdapter extends ArrayAdapter<Bid> {
        public BidAdapter(Context context, ArrayList<Bid> bids) {
            super(context, 0, bids);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Bid bid = getItem(position);
            Double amount = bid.getAmount();
            String bidder = bid.getBidderUsername();

            final ChickenController chickenController = ChickBidsApplication.getChickenController();
            final UserController userController = ChickBidsApplication.getUserController();

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bid, parent, false);
            }

            TextView bidAmount = (TextView) convertView.findViewById(R.id.bidAmount);
            TextView bidderUsername = (TextView) convertView.findViewById(R.id.bidderUsername);
            ImageView acceptBidImageView = (ImageView) convertView.findViewById(R.id.acceptBidImageView);
            ImageView declineBidImageView = (ImageView) convertView.findViewById(R.id.declineBidImageView);

            // Populate the data into the template view using the data object
            bidAmount.setText(amount.toString());
            bidderUsername.setText(bidder);

            acceptBidImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptBid(bid);
                }
            });

            declineBidImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        chickenController.rejectBidForChicken(bid);
                    } catch (ChickenException e) {
                        e.printStackTrace();
                    }
                    refreshView();
                }
            });

            //hide accept/decline buttons if not owner
            if ((bid.getBidStatus() == Bid.BidStatus.UNDECIDED)
                && (chickenController.getCurrentChicken().getOwnerUsername().equals(userController.getCurrentUser().getUsername()))) {
                acceptBidImageView.setVisibility(View.VISIBLE);
                declineBidImageView.setVisibility(View.VISIBLE);
            } else {
                acceptBidImageView.setVisibility(View.GONE);
                declineBidImageView.setVisibility(View.GONE);
            }

            // Return the completed view to render on screen
            return convertView;
        }
    }
}

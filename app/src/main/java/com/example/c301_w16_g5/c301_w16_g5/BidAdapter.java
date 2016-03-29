package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BidAdapter extends ArrayAdapter<Bid> {
    public BidAdapter(Context context, ArrayList<Bid> bids) {
        super(context, 0, bids);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bid bid = getItem(position);
        Double amount = bid.getAmount();
        String bidder = bid.getBidderUsername();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bid, parent, false);
        }

        TextView bidAmount = (TextView) convertView.findViewById(R.id.bidAmount);
        TextView bidderUsername = (TextView) convertView.findViewById(R.id.bidderUsername);

        // Populate the data into the template view using the data object
        bidAmount.setText(amount.toString());
        bidderUsername.setText(bidder);

        // Return the completed view to render on screen
        return convertView;
    }
}

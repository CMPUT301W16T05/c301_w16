package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <code>ViewPhotoActivity</code> provides a view of the photo attached to a
 * chicken's profile.  This view is only accessible when the corresponding
 * chicken actually has a photo attached to it.
 *
 * @author  Hailey
 * @version 1.5, 03/27/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class ViewPhotoActivity extends ChickBidActivity {

    TextView name;
    ImageView imageChicken;

    Chicken currentChicken;
    User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        current_user = ChickBidsApplication.getUserController().getCurrentUser();

        imageChicken = (ImageView) findViewById(R.id.imageChicken);
        name = (TextView) findViewById(R.id.name);

        if(currentChicken.getOwnerUsername().equals(current_user.getUsername()) && currentChicken.getPhoto() != null) {
            registerForContextMenu(imageChicken);
        }

        name.setText(currentChicken.getName());
        if(currentChicken.getPhoto() != null){
            imageChicken.setImageBitmap(currentChicken.getPhoto());
        } else {
            imageChicken.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete:
                if (ChickBidsApplication.getSearchController().checkOnline()) {
                    if (currentChicken.getOwnerUsername().equals(current_user.getUsername())) {
                        imageChicken.setImageDrawable(null);
                        currentChicken.clearPhoto();
                        ChickBidsApplication.getChickenController().updateChickenForMe(currentChicken);
                    } else {
                        Toast.makeText(this, "Cannot delete a borrowed chicken image.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Cannot delete a chicken image offline.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return true;
    }
}

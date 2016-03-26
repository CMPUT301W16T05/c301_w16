package com.example.c301_w16_g5.c301_w16_g5;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoActivity extends ChickBidActivity {

    TextView name;
    ImageView imageChicken;

    Chicken currentChicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();

        imageChicken = (ImageView) findViewById(R.id.imageChicken);
        name = (TextView) findViewById(R.id.name);

        name.setText(currentChicken.getName());
        if(currentChicken.getPicture() != null){
            imageChicken.setImageURI(currentChicken.getPicture());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}

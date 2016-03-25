package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This view displays the profile information for a particular chicken.
 *
 * @author  Robin
 * @version 1.4, 03/12/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class ChickenProfileActivity extends ChickBidActivity {

    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int RESULT_UPDATE_CHICKEN = 2;

    TextView name;
    TextView description;
    TextView status;
    ImageView chickenImage;

    Button buttonEdit;
    Button buttonViewBids;

    Chicken currentChicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        currentChicken = b.getParcelable("selectedChicken");

        chickenImage = (ImageView) findViewById(R.id.chickenImage);

        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonViewBids = (Button) findViewById(R.id.buttonViewBids);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEditChicken();
            }
        });

        buttonViewBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTextFields(currentChicken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_UPDATE_CHICKEN && resultCode == RESULT_OK && data != null){
            Chicken new_chicken = data.getParcelableExtra(EditChickenActivity.EDITED_CHICKEN);
            ChickBidsApplication.getChickenController().updateChickenForMe(new_chicken);
            setTextFields(new_chicken);
            showToast(new_chicken.getPicture().toString());
        }
    }

    private void setTextFields(Chicken chicken){
        name.setText(chicken.getName());
        description.setText(chicken.getDescription());
        status.setText(chicken.getChickenStatus().toString());
        if(chicken.getPicture() != null){
            chickenImage.setImageURI(chicken.getPicture());
        }
    }

    protected void launchEditChicken(){
        Intent editChickenIntent = new Intent(this, EditChickenActivity.class);
        editChickenIntent.putExtra(EditChickenActivity.EDITING_CHICKEN, currentChicken);
        startActivityForResult(editChickenIntent, RESULT_UPDATE_CHICKEN);
    }

    //TODO: Remove when edit chicken is done.
    private void launch_GalleryPick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private void showToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

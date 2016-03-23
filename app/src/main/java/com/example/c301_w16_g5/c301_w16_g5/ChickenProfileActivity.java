package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView tvName, tvOwner, tvDescription, tvBorrower, tvStatus;
    ImageView chickenImage;
    //Button bUploadImage;

    FloatingActionButton fab;
    //Spinner status;
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
        //bUploadImage = (Button) findViewById(R.id.bUploadImage);

        tvName = (TextView) findViewById(R.id.tvName);
        tvOwner = (TextView) findViewById(R.id.tvOwner);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvBorrower = (TextView) findViewById(R.id.tvBorrower);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        fab = (FloatingActionButton) findViewById(R.id.chickenfab);
        //status = (Spinner) findViewById(R.id.editstatusSpinner);
        //status.setAdapter(new ArrayAdapter<Enum>(this, android.R.layout.simple_spinner_dropdown_item, Chicken.ChickenStatus.values()));

/*
        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_GalleryPick();
            }
        });
*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               launch_EditChicken(currentChicken);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            currentChicken.setPhoto(new Photograph(selectedImage));
            chickenImage.setImageURI(selectedImage);
        }
        if(requestCode == RESULT_UPDATE_CHICKEN && resultCode == RESULT_OK && data != null){
            Chicken new_chicken = data.getParcelableExtra(EditChickenActivity.EDITTED_CHICKEN);
            ChickBidsApplication.getChickenController().updateChickenForMe(new_chicken);
            setTextFields(new_chicken);
        }
    }

    private void setTextFields(Chicken chicken){
        tvName.setText(chicken.getName());
        tvOwner.setText(chicken.getOwnerUsername());
        tvBorrower.setText(chicken.getBorrowerUsername());
        tvDescription.setText(chicken.getDescription());
        tvStatus.setText(chicken.getChickenStatus().toString());
        if(chicken.getPicture() != null){
            chickenImage.setImageURI(chicken.getPicture());
        };
    }

    private void launch_EditChicken(Chicken chicken){
        Intent editChickenIntent = new Intent(this, EditChickenActivity.class);
        editChickenIntent.putExtra(EditChickenActivity.EDITTING_CHICKEN, currentChicken);
        startActivityForResult(editChickenIntent, RESULT_UPDATE_CHICKEN);
    }

    //TODO: Remove when edit chicken is done.
    private void launch_GalleryPick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
}

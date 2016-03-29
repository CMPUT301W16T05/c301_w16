package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <code>EditChickenActivity</code> provides a view from which a user can update
 * the description and/or photo of one of their own chickens.
 *
 * @author  Hailey, Robin
 * @version 1.5, 03/27/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class EditChickenActivity extends ChickBidActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    Chicken currentChicken;

    TextView name;
    TextView status;
    EditText description;

    ImageView imageToUpload;
    Button buttonUploadImage, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chicken);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        description = (EditText) findViewById(R.id.description);

        imageToUpload = (ImageView) findViewById(R.id.imageChicken);
        buttonUploadImage = (Button) findViewById(R.id.buttonUploadImage);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGalleryPick();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTextFields();
                ChickBidsApplication.getChickenController().updateChickenForMe(currentChicken);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        setTextFields(currentChicken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            currentChicken.setPicture(selectedImage);
            imageToUpload.setImageURI(selectedImage);
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

    private void setTextFields(Chicken chicken){
        name.setText(chicken.getName());
        status.setText(chicken.getChickenStatus().toString());
        description.setText(chicken.getDescription());

        if(chicken.getPicture() != null){
            imageToUpload.setImageURI(chicken.getPicture());
        }
    }

    private void saveTextFields(){
        currentChicken.setDescription(description.getText().toString());
    }

    private void launchGalleryPick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

}
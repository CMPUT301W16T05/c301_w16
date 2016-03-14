package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Spinner;

/**
 * This view displays the profile information for a particular chicken.
 *
 * @author  Robin
 * @version 1.4, 03/12/2016
 * @see     Chicken
 * @see     ChickenController
 */
public class ChickenProfileActivity extends ChickBidActivity {

    //TODO:
    /* Error in saving changes - they arn't staying, and causing clones
     * in owners chicken. Attempting to use ChickenController.
     *
     * Error in saving photo - attempting to load an edit with photo attached leads
     * to null errors. Potential issue with parcelable in photo.
     *
     * More UI tweaks still needed.
     */

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage, saveButton;
    EditText etName, etDescription;
    Spinner status;
    Chicken editChicken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        editChicken = b.getParcelable("chickenEdit");

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        saveButton = (Button) findViewById(R.id.editChicken_saveButton);

        etName = (EditText) findViewById(R.id.etName);
        status = (Spinner) findViewById(R.id.editstatusSpinner);
        status.setAdapter(new ArrayAdapter<Enum>(this, android.R.layout.simple_spinner_dropdown_item, Chicken.ChickenStatus.values()));
        etDescription = (EditText) findViewById(R.id.etDescription);

        //bUploadImage.setOnClickListener(this);
        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //ChickBidsApplication.getChickenController().updateChickenForMe(editChicken);

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        etName.setText(editChicken.getName());
        etDescription.setText(editChicken.getDescription());
      //  if(editChicken.getPhoto().getImage() != null) {
      //      imageToUpload.setImageURI(editChicken.getPhoto().getImage());
      //  }
    }

/*
    @Override
    public void onClick(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
   */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            editChicken.setPhoto(new Photograph(selectedImage));
            imageToUpload.setImageURI(selectedImage);
        }
    }



}

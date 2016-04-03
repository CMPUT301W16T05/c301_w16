package com.example.c301_w16_g5.c301_w16_g5;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import java.io.IOException;

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

    String currentDescription;
    Bitmap currentPhoto;

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
                currentDescription = description.getText().toString();
                launchGalleryPick();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChickenInfoFields();
                ChickBidsApplication.getChickenController().updateChickenForMe(currentChicken);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentChicken = ChickBidsApplication.getChickenController().getCurrentChicken();
        setChickenInfoFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                currentPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Photo upload failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void setChickenInfoFields() {
        name.setText(currentChicken.getName());
        status.setText(currentChicken.getChickenStatus().toString());
        description.setText(currentChicken.getDescription());

        if (currentPhoto != null) {
            // if a photo was just uploaded, display it even if it hasn't been
            // saved
            imageToUpload.setImageBitmap(currentPhoto);
        } else if (currentChicken.getPhoto() != null) {
            // if no photo was just selected, but one is stored for the
            // chicken, display it
            imageToUpload.setImageBitmap(currentChicken.getPhoto());
        }  // otherwise, no photo available

        if (currentDescription != null) {
            description.setText(currentDescription);
        }
    }

    private void saveChickenInfoFields() {
        // save the most recently input data
        currentChicken.setDescription(description.getText().toString());
        currentChicken.setPhoto(currentPhoto);
    }

    private void launchGalleryPick() {
        // choose a photo from the phone's gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

}
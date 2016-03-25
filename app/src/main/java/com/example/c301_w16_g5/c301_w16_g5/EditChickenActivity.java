package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditChickenActivity extends AppCompatActivity {

    public static final String EDITTING_CHICKEN = "editting chicken";
    public static final String EDITTED_CHICKEN = "editted chicken";
    private static final int RESULT_LOAD_IMAGE = 1;

    Chicken currentChicken;

    EditText etName, etOwner, etDescription, etBorrower, etStatus;
    ImageView imageToUpload;
    Button bUploadImage, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chicken);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.editChicken_etName);
        etStatus = (EditText) findViewById(R.id.editChicken_etStatus);
        etDescription = (EditText) findViewById(R.id.editChicken_etDescription);
        imageToUpload = (ImageView) findViewById(R.id.editChicken_imageToUpload);
        bUploadImage = (Button) findViewById(R.id.editChicken_bUploadImage);
        saveButton = (Button) findViewById(R.id.editChicken_saveButton);

        Bundle b = getIntent().getExtras();
        currentChicken = b.getParcelable(EDITTING_CHICKEN);

        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch_GalleryPick();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveTextFields();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EDITTED_CHICKEN, currentChicken);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            currentChicken.setPicture(selectedImage);
            imageToUpload.setImageURI(selectedImage);
        }
    }


    private void setTextFields(Chicken chicken){
        etName.setText(chicken.getName());
        //etOwner.setText(chicken.getOwnerUsername());
        //tvOwner.setText(chicken.getBorrowerUsername());
        etDescription.setText(chicken.getDescription());
        etStatus.setText(chicken.getChickenStatus().toString());
        if(chicken.getPicture() != null){
            imageToUpload.setImageURI(chicken.getPicture());
        };
    }

    private void saveTextFields(){
        currentChicken.setName(etName.getText().toString());
        currentChicken.setDescription(etDescription.getText().toString());
    }

    private void launch_GalleryPick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

}

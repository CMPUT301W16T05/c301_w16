package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditChickenActivity extends AppCompatActivity {

    public static final String EDITING_CHICKEN = "editing chicken";
    public static final String EDITED_CHICKEN = "edited chicken";
    private static final int RESULT_LOAD_IMAGE = 1;

    Chicken currentChicken;

    TextView name;
    TextView status;
    EditText description;

    ImageView imageToUpload;
    Button bUploadImage, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chicken);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // show back arrow at top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.editChicken_etName);
        status = (TextView) findViewById(R.id.editChicken_etStatus);
        description = (EditText) findViewById(R.id.editChicken_etDescription);

        imageToUpload = (ImageView) findViewById(R.id.editChicken_imageToUpload);
        bUploadImage = (Button) findViewById(R.id.editChicken_bUploadImage);
        saveButton = (Button) findViewById(R.id.editChicken_saveButton);

        Bundle b = getIntent().getExtras();
        currentChicken = b.getParcelable(EDITING_CHICKEN);

        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGalleryPick();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveTextFields();
                // TODO: uncomment once updateChickenForMe works
//                ChickBidsApplication.getChickenController().updateChickenForMe(currentChicken);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EDITED_CHICKEN, currentChicken);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent viewChickenIntent = new Intent(this, MyChickenDisplayProfileActivity.class);
                viewChickenIntent.putExtra("selectedChicken", currentChicken);
                startActivity(viewChickenIntent);
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
        };
    }

    private void saveTextFields(){
        currentChicken.setDescription(description.getText().toString());
    }

    private void launchGalleryPick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

}
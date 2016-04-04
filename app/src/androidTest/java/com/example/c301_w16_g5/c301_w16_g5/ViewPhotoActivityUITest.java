package com.example.c301_w16_g5.c301_w16_g5;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Hailey on 2016-03-29.
 */
public class ViewPhotoActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;

    public ViewPhotoActivityUITest() {
        super(ViewPhotoActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        User user = new User("un", "f", "l", "abc@email.com", "780-123-4567", "some");
        ChickBidsApplication.getChickenController().setCurrentChicken(new Chicken("Bob", "Friendly chicken", user.getUsername()));

        instrumentation = getInstrumentation();
        activity = getActivity();
    }

    public void testToolbarVisible() {
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),
                activity.findViewById(R.id.nav_toolbar));
    }

    public void testPhotoVisibility() {
        ImageView imageChicken = (ImageView) activity.findViewById(R.id.imageChicken);

        if (imageChicken.getVisibility() != View.INVISIBLE) {
            fail();
        }

        String Filepath = ""; //TODO: Replace value with whatever file is set in memory.
        Bitmap currentPhoto = BitmapFactory.decodeFile(Filepath);
        if(currentPhoto != null){
            imageChicken.setImageBitmap(currentPhoto);
        }
        imageChicken.setImageBitmap(currentPhoto);

    }
}
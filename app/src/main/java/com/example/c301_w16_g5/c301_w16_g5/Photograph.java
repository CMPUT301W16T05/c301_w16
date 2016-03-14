package com.example.c301_w16_g5.c301_w16_g5;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A user may choose to attach a <code>Photograph</code> to the profile of one
 * of their chickens.  This will allow potential borrowers to better assess the
 * chicken they are bidding on.
 *
 * @author  Shahzeb
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Chicken
 * @see     AddChickenActivity
 * @see     ChickenActivity
 * @see     EditProfileActivity
 * @see     ChickenController
 */
public class Photograph implements Parcelable{

    private Uri image = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Photograph(Uri image_) {
        this.image = image_;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    protected Photograph(Parcel in) {
    }

    public static final Creator<Photograph> CREATOR = new Creator<Photograph>() {
        public Photograph createFromParcel(Parcel source) {
            return new Photograph(source);
        }

        public Photograph[] newArray(int size) {
            return new Photograph[size];
        }
    };
}

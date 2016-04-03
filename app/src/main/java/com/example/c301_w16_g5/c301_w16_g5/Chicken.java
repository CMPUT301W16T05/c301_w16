package com.example.c301_w16_g5.c301_w16_g5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.annotations.JestId;

/**
 * A <code>Chicken</code> is the item of trade around which the application is
 * based.  Users borrow chickens from other users who own them, determined by
 * a bidding system.  Chickens are searchable in a database.
 *
 * @author  Robin
 * @version 1.4, 03/02/2016
 * @see     User
 * @see     Bid
 * @see     ElasticSearchBackend
 * @see     ChickenController
 * @see     AddChickenActivity
 * @see     EditChickenActivity
 * @see     MyChickenDisplayProfileActivity
 * @see     OthersChickenDisplayProfileActivity
 */
public class Chicken extends GenericModel<GenericView> implements Parcelable {
    public enum ChickenStatus {
        AVAILABLE,
        BORROWED,
        BIDDED,
        NOT_AVAILABLE
    }
    @JestId
    protected String id;

    private String name;
    private String description;
    private ChickenStatus chickenStatus;
    private String ownerUsername;
    private String borrowerUsername;
    private ArrayList<Bid> bids;
    //private Uri picture;
    private Bitmap photo;
    private String photoBase64;

    static final int MAX_IMAGE_SIZE = 65536; // max final file size

    protected Chicken() {
        bids = new ArrayList<Bid>();
    }

    public Chicken(String name, String description, String ownerUsername) {
        this.name = name;
        this.description = description;
        this.chickenStatus = ChickenStatus.AVAILABLE;
        this.ownerUsername = ownerUsername;
        this.bids = new ArrayList<Bid>();
    }

    public void update(String name, String description, ChickenStatus chickenStatus) {
        this.name = name;
        this.description = description;
        this.chickenStatus = chickenStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChickenStatus getChickenStatus() {
        return chickenStatus;
    }

    public void setChickenStatus(ChickenStatus chickenStatus) {
        this.chickenStatus = chickenStatus;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String owner_username) {
        this.ownerUsername = owner_username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void deleteBidForId(String id) {
        ArrayList<Bid> bids = getBids();
        for (Bid b : bids) {
            if (b.getId().equals(id)) {
                bids.remove(b);
                break;
            }
        }
    }

    public String getBorrowerUsername() {
        return borrowerUsername;
    }

    public void setBorrowerUsername(String borrower_username) {
        this.borrowerUsername = borrower_username;
    }

    public Bitmap getPhoto() {
        if (photo == null && photoBase64 != null) {
            byte[] decodeString = Base64.decode(photoBase64, Base64.DEFAULT);
            photo = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return photo;
    }

    /**
     * Sets the photo of the chicken to the provided photo, compressing this
     * photo to a maximum of 65536 bytes.
     *
     * @param newPhoto  the bitmap of the new photo
     */
    public void setPhoto(Bitmap newPhoto) {
        if (newPhoto != null) {
            photo = newPhoto;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] b = stream.toByteArray();

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 2;  // cut width & height in half

            Log.d("INITIAL SIZE", Integer.toString(photo.getByteCount()));
            while (photo.getByteCount() >= MAX_IMAGE_SIZE) {
                // decrease photo size to 1 quarter
                photo = BitmapFactory.decodeByteArray(b, 0, b.length, opts);

                // get the byte array of the downsized photo
                stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                b = stream.toByteArray();
                Log.d("SIZE", Integer.toString(photo.getByteCount()));
            }
            Log.d("FINAL SIZE", Integer.toString(photo.getByteCount()));
            photoBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public void clearPhoto() {
        photo = null;
        photoBase64 = null;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*
    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }
    */

    // TODO: Delete
    // Generated by Android Parcelable Generator
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.chickenStatus == null ? -1 : this.chickenStatus.ordinal());
        dest.writeString(this.ownerUsername);
        dest.writeString(this.borrowerUsername);
        dest.writeList(this.bids);
        dest.writeParcelable(this.photo, flags);
    }

    protected Chicken(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        int tmpChickenStatus = in.readInt();
        this.chickenStatus = tmpChickenStatus == -1 ? null : ChickenStatus.values()[tmpChickenStatus];
        this.ownerUsername = in.readString();
        this.borrowerUsername = in.readString();
        this.bids = new ArrayList<Bid>();
        in.readList(this.bids, List.class.getClassLoader());
        this.photo = in.readParcelable(Photograph.class.getClassLoader());
    }

    public static final Creator<Chicken> CREATOR = new Creator<Chicken>() {
        public Chicken createFromParcel(Parcel source) {
            return new Chicken(source);
        }

        public Chicken[] newArray(int size) {
            return new Chicken[size];
        }
    };
}

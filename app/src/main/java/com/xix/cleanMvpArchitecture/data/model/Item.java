package com.xix.cleanMvpArchitecture.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

    @SerializedName("image") @Expose private String mImageUrl;
    @SerializedName("description") @Expose private String mDescription;
    @SerializedName("title") @Expose private String mTitle;

    private int mDominantColor = 0;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImage(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getDominantColor() {
        return mDominantColor;
    }

    public void setDominantColor(int dominantColor) {
        mDominantColor = dominantColor;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImageUrl);
        dest.writeString(this.mDescription);
        dest.writeString(this.mTitle);
        dest.writeInt(this.mDominantColor);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.mImageUrl = in.readString();
        this.mDescription = in.readString();
        this.mTitle = in.readString();
        this.mDominantColor = in.readInt();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override public String toString() {
        return "Item{"
            + "mImageUrl='"
            + mImageUrl
            + '\''
            + ", mDescription='"
            + mDescription
            + '\''
            + ", mTitle='"
            + mTitle
            + '\''
            + ", mDominantColor="
            + mDominantColor
            + '}';
    }
}
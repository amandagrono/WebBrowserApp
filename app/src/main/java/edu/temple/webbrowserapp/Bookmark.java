package edu.temple.webbrowserapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Bookmark implements Parcelable, Serializable {

    private String url;
    private String title;

    public Bookmark(String url, String title){
        this.url = url;
        this.title = title;
    }

    protected Bookmark(Parcel in) {
        url = in.readString();
        title = in.readString();
    }

    public String getUrl(){
        return url;
    }
    public String getTitle(){
        return title;
    }
    public String toString(){
        return title;
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean retVal = false;

        if(obj instanceof Bookmark){
            retVal = ((Bookmark) obj).title.equals(title);
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.title != null ? this.title.hashCode() : 0);
        return hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
    }
}

package com.rahuldshetty.rosaryaudioapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.method.MovementMethod;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class SubTitles implements Parcelable {

    String title;
    Bitmap image;
    String desc;
    String path;
    String songPath;
    String fileName;

    public SubTitles(Parcel in) {
        title = in.readString();
        desc = in.readString();
        path = in.readString();
        songPath = in.readString();
        fileName = in.readString();
        type = in.readString();
        image = null;
    }

    public static final Creator<SubTitles> CREATOR = new Creator<SubTitles>() {
        @Override
        public SubTitles createFromParcel(Parcel in) {
            return new SubTitles(in);
        }

        @Override
        public SubTitles[] newArray(int size) {
            return new SubTitles[size];
        }
    };



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SubTitles(String title, Bitmap image, String desc, String path, String songPath, String fileName, String type) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.path = path;
        this.songPath = songPath;
        this.fileName = fileName;
        this.type = type;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public SubTitles(String title, Bitmap image, String desc, String path, String songPath, String type) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.path = path;
        this.songPath = songPath;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public SubTitles(String title, Bitmap image, String desc, String path, String type) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.path = path;
        this.type = type;
    }

    public SubTitles(String title, Bitmap image, String desc, String path) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.path = path;
    }

    public  SubTitles(){

    }

    public void getAll(){
        System.out.println("DEBUG:"+title+" " + desc + " " + path+ " " + image.getHeight());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(path);
        dest.writeString(songPath);
        dest.writeString(fileName);
        dest.writeString(type);
    }
}

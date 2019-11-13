package com.rahuldshetty.rosaryaudioapp.models;

import android.graphics.Bitmap;

public class Title {
    String text;
    Bitmap album;
    int id;
    String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Title(String text, Bitmap album, int id, String type) {
        this.text = text;
        this.album = album;
        this.id = id;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getAlbum() {
        return album;
    }

    public void setAlbum(Bitmap album) {
        this.album = album;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Title(){

    }

    public Title(String text, Bitmap album, int id) {
        this.text = text;
        this.album = album;
        this.id = id;
    }
}

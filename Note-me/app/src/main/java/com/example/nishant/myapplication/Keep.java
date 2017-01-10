package com.example.nishant.myapplication;

import android.graphics.Bitmap;

/**
 * Created by nishant on 1/1/2017.
 */

public class Keep {
    int id;
    String title, note;
    Bitmap picture;
    String remdet;

    public Keep(int id,String title,String note,Bitmap picture,String remdet) {
        this.remdet = remdet;
        this.picture = picture;
        this.note = note;
        this.title = title;
        this.id = id;
    }

    public String getRemdet() {

        return remdet;
    }

    public void setRemdet(String remdet) {
        this.remdet = remdet;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}

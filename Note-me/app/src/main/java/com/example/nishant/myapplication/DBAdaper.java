package com.example.nishant.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;

import static com.example.nishant.myapplication.Constant.ROW_ID;

/**
 * Created by nishant on 1/1/2017.
 */

public class DBAdaper {
    Context context;
    DBHelper helper;
    SQLiteDatabase db;

    public DBAdaper(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public DBAdaper open() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        try {
            helper.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public long insert(String title, String note, Bitmap picture, String remdetail) {
        byte[] blob = Utility.getBytes(picture);

        try {
            ContentValues cv = new ContentValues();
            cv.put(Constant.TITLE, title);
            cv.put(Constant.NOTE, note);
            cv.put(Constant.PICTURE, blob);
            cv.put(Constant.REMDETAIL, remdetail);

            return db.insert(Constant.TB_NAME, ROW_ID, cv);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor getALLinfo() {
        String COLUMN[] = {ROW_ID, Constant.TITLE, Constant.NOTE, Constant.PICTURE,Constant.REMDETAIL};
        return db.query(Constant.TB_NAME, COLUMN, null, null, null, null, null);
    }

    public long update(int id, byte[] picbyte, String title, String note, String remdetails) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constant.PICTURE, picbyte);
            cv.put(Constant.TITLE, title);
            cv.put(Constant.NOTE, note);
            cv.put(Constant.REMDETAIL,remdetails);
            return db.update(Constant.TB_NAME, cv, ROW_ID + " =?", new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean Delete(int id) {
        try {
            int result= db.delete(Constant.TB_NAME, ROW_ID + " =?", new String[]{String.valueOf(id)});
        if (result>0)
        {
            return true;
        }

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;
    }


}

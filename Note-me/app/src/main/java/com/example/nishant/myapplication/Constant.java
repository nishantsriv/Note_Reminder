package com.example.nishant.myapplication;

/**
 * Created by nishant on 1/1/2017.
 */

public class Constant {
    //Columns
    static final String ROW_ID = "id";
    static final String TITLE = "title";
    static final String NOTE = "note";
    static final String PICTURE="picture";
    static final String REMDETAIL="remdetail";

    //DB PROPS
    static final String DB_NAME="a_DB";
    static final String TB_NAME="a_TB";
    static final int DB_VERSION='1';

    static final String CREATE_TB="CREATE TABLE a_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "title TEXT ,note TEXT ,picture BLOB ,remdetail TEXT);";
}

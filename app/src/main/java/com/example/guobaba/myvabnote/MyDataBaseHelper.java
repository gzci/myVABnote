package com.example.guobaba.myvabnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE dict(_id INTEGER PRIMARY KEY autoincrement ,word,detail)";
        String sql1 = "CREATE TABLE newdict(_id INTEGER PRIMARY KEY autoincrement ,word,detail)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
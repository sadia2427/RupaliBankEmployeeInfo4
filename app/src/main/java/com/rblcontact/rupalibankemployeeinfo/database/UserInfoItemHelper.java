package com.rblcontact.rupalibankemployeeinfo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoItemHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="USER_DATABASE";
    public static final int DATABASE_VERSION= 1;

    public UserInfoItemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

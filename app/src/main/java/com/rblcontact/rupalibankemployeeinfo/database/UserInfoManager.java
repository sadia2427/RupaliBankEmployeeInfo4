package com.rblcontact.rupalibankemployeeinfo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserInfoManager {
    private SQLiteDatabase sqLiteDatabase;
    private UserInfoItemHelper userInfoItemHelper;

    public UserInfoManager(Context context){
        userInfoItemHelper=new UserInfoItemHelper(context);
    }

    private void open(){
        sqLiteDatabase=userInfoItemHelper.getWritableDatabase();
    }
    private void close(){
        userInfoItemHelper.close();
    }
    public boolean insertItem(UserInfoModel userInfoModel){
        this.open();
        ContentValues cv=new ContentValues();
        return true;
    }
}

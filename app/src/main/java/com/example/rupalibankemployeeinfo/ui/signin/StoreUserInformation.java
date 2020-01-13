package com.example.rupalibankemployeeinfo.ui.signin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class StoreUserInformation {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME_USER_DATA = "AjkerdealprefForUserInformation";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String MOBILE_NUMBER = "mobilenumber";
    public static final String GENDER="gender";
    public static final String ADDRESS="address";
    public static final String DISTRICT_BANGLA = "districtbangla";
    public static final String DISTRICT_ENGLIS = "districtenglish";
    public static final String KNOW_ABOUT_US = "knowaboutus";
    public static final String DISTRICT_ID_PRF = "district_id";
    public static final String AREA_ID_PRF = "area_id";




    public StoreUserInformation(Context context){
        this.mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME_USER_DATA, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public void storeUserInformation(String email, String name, String mobile,  String address){

        mEditor.putString(EMAIL,email);
        mEditor.putString(NAME,name);
        mEditor.putString(MOBILE_NUMBER,mobile);
        mEditor.putString(ADDRESS,address);
        mEditor.commit();
    }


    public void clearInformation(Context context) {
        // Clearing all data from Shared Preferences
        if (context != null) {
            mEditor.clear();
            mEditor.commit();
        }
    }

    public HashMap<String, String> getUserInformation(){
        HashMap<String, String> UserInformation = new HashMap<String, String>();

        UserInformation.put(EMAIL, mPref.getString(EMAIL, ""));
        UserInformation.put(NAME, mPref.getString(NAME, ""));
        UserInformation.put(MOBILE_NUMBER, mPref.getString(MOBILE_NUMBER, ""));
        UserInformation.put(ADDRESS, mPref.getString(ADDRESS, ""));

        return UserInformation;
    }

}

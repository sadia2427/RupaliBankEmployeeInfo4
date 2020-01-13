package com.example.rupalibankemployeeinfo.api.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SignInInfromation {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private static final String PREF_SIZE = "RupaliBankprefsignin";
    public static final String RUPALIBANK_SIGNIN = "RupaliBanksignin";


    public SignInInfromation(Context context) {
        this.mContext = context;
        mPref = mContext.getSharedPreferences(PREF_SIZE, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public void storeSignin(int ad, int facebook, int google) {

        mEditor.putInt(RUPALIBANK_SIGNIN, ad);
        mEditor.commit();
    }

    public void clearSignin(Context context) {
        // Clearing all data from Shared Preferences
        if (mContext != null) {

            mEditor.clear();
            mEditor.commit();
        }
    }

    public HashMap<String, Integer> getSigninInformation() {
        HashMap<String, Integer> SignInformation = new HashMap<>();

        SignInformation.put(RUPALIBANK_SIGNIN, mPref.getInt(RUPALIBANK_SIGNIN, -1));

        return SignInformation;
    }

}

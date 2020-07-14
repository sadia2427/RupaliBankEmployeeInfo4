package com.rblcontact.rupalibankemployeeinfo.ui.signin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "RupaliBank";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String UserIdKey="userIdKey";
    public static final String PhoneKey="phoneKey";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String password, String email, String userMobile, int userRegistrationId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(PhoneKey,userMobile);

        editor.putInt(UserIdKey,userRegistrationId);

        // commit changes
        editor.commit();
    }

    public String getReg(){
        String reg="";

        return KEY_EMAIL.toString();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(PhoneKey, pref.getString(PhoneKey, null));

        // return user
        return user;
    }


    public HashMap<String, Integer> getUserID(){
        HashMap<String, Integer> user = new HashMap<>();
        user.put(UserIdKey,pref.getInt(UserIdKey,-1));
        return  user;
    }


    public void logoutUser(Context context){
        // Clearing all data from Shared Preferences
        if (context != null) {
//            mCartManager=new CartManager(context);
//            mCartManager.deleteall();
//            Realm.init(context);
//
////        add migration
//            RealmConfiguration config = new RealmConfiguration
//                    .Builder()
//                    .deleteRealmIfMigrationNeeded()
//                    .build();
//            mRealmDealIDList = Realm.getInstance(config);
//
//            mRealmDealIDList.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    RealmResults<CartModel> cartModels = realm.where(CartModel.class).findAll();
//                    cartModels.deleteAllFromRealm();
//                    HomeActivity.mCartTextView.setText("");
//                    HomeActivity.mCartTextView.setVisibility(View.GONE);
//                }
//            });
            editor.clear();
            editor.commit();
        }

//        // After logout redirect user to Loing Activity
//        Intent i = new Intent(_context, LoginActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);


    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}

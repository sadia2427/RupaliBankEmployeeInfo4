package com.rblcontact.rupalibankemployeeinfo;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rblcontact.rupalibankemployeeinfo.api.model.SignInInfromation;
import com.rblcontact.rupalibankemployeeinfo.ui.changepassword.ChangePasswordFragment;
import com.rblcontact.rupalibankemployeeinfo.ui.signin.SessionManager;
import com.rblcontact.rupalibankemployeeinfo.ui.signin.SignInActivity;
import com.rblcontact.rupalibankemployeeinfo.ui.signin.StoreUserInformation;


import android.util.Log;
import android.view.MenuItem;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
//    SearchView searchView;
    private Toolbar toolbar;
    private static final int REQUEST_CALL=123;
    private SessionManager session;
    private StoreUserInformation mStoreUserInformation;
    private SignInInfromation mSignInInfromation;

    private AppBarConfiguration mAppBarConfiguration;
    private String mRegID;
    private SharedPreferences sharedPreferences;
    private CircleImageView profileImage;
    private ProgressBar profile_ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("rupaliCache", 0);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("rupaliCache",0);

        ChangePasswordFragment changePasswordFragment= new  ChangePasswordFragment();
          Bundle userInfo =new Bundle();
          userInfo.putString("regNo1","13945");
        changePasswordFragment.setArguments(userInfo);
        ////////////////////////////---PERMISSION GRANTED FOR USER PHONE CALL---//////////////////////////////

    /*    if((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_CALL_LOG,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CALL);//REQUEST_CALL
            Log.w("SEND_SMS PERMISSION ", "NEW SEND_SMS PERMISSION IS GRANTED");


        }else{
            Log.w("SEND_SMS PERMISSION ", "SEND_SMS PERMISSION ALRADY GRANTED");
        }
*/
        //Load RegID for image
        mRegID = sharedPreferences.getString(SessionManager.KEY_EMAIL, "");
        Log.w("regNo ", "regNo Not fouNd:------------------------- |"+mRegID+"|");
        if(mRegID==null||mRegID==""||mRegID=="null")
        {
            mRegID = sharedPreferences.getString("mobile","");
            Log.w("regNo ", "regNo Not fouNd:-------------------------2 |"+mRegID+"|");
            Log.w("regNo ", "http://103.125.136.110/picture/photos/" + mRegID + ".jpg");
        }

//        searchView=findViewById(R.id.search_item);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // set action of fab button
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                SearchFragment fragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.example_fragment);
////                fragment.<specific_function_name>();
//                Fragment fragment=new SearchFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.nav_host_fragment, fragment);
//                ft.addToBackStack(null);
//                fm.popBackStackImmediate();
//                ft.commit();
//            }
//        });
        session=new SessionManager(getApplicationContext());
        mStoreUserInformation=new StoreUserInformation(getApplicationContext());
        mSignInInfromation=new SignInInfromation(getApplicationContext());

//        if (!session.isLoggedIn()){
//
//            Intent intent = new Intent(MainActivity.this,SignInActivity.class);
//            startActivity(intent);
//            finish();
//        }

        if(getIntent().getStringExtra("changePass")!=null){
            Bundle mBundle=new Bundle();
            Log.e("regNO Main", "onCreate: "+String.valueOf(getIntent().getStringExtra("changePass") ));
            mBundle.putString("regNo", String.valueOf(getIntent().getStringExtra("changePass")));
            Fragment fragment = new ChangePasswordFragment();
            fragment.setArguments(mBundle);

            //getActivity().getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView userNameTv = hView.findViewById(R.id.userNameTv);
        TextView userMobileTv = hView.findViewById(R.id.userMobileTv);
         profileImage = hView.findViewById(R.id.profileImage);
        profile_ProgressBar=  hView.findViewById(R.id.profile_progressBar);

         /*try{
             Glide.with(this)
                     .load("http://103.125.136.110/picture/photos/" + mRegID + ".jpg")
                     // .error(R.drawable.download_error)
                     .into(profileImage);
             Log.w("Url---- ", profileImage.toString());
         }catch(Exception e){

            *//* //////////////////////////////////////////////////////

             String urlLink="http://103.125.136.110/picture/photos/" + mRegID + ".jpg";
             if(urlLink.isEmpty()){
                 // Toast.makeText(getApplicationContext(),"Please Enter Url!!",Toast.LENGTH_SHORT).show();
             }else{
                 LoadImage loadImage= new LoadImage(profileImage);
                 loadImage.execute(urlLink);


             }
             ////////////////////////////////////////////////////*//*
         }*/


   //////////////////////////////////////////////////////

             String urlLink="http://103.125.136.110/picture/photos/" + mRegID + ".jpg";
             if(urlLink.isEmpty()){
                 // Toast.makeText(getApplicationContext(),"Please Enter Url!!",Toast.LENGTH_SHORT).show();
             }else{
                 LoadImage loadImage= new LoadImage(profileImage);
                 loadImage.execute(urlLink);


             }
             ////////////////////////////////////////////////////


      /*  Glide.with(this)
                .load("http://103.125.136.110/picture/photos/13945.jpg")//.override(3,3)//.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
               .into(profileImage);//https://rawlsresearchlab.ba.ttu.edu/images/BL.jpg  //http://103.125.136.110/picture/photos/13945.jpg
*/
        userMobileTv.setText(sharedPreferences.getString("mobile",""));
        userNameTv.setText(sharedPreferences.getString("userName",""));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search,R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_about,R.id.nav_changepassword, R.id.nav_notices, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        ////////////////////////////---PERMISSION GRANTED FOR USER PHONE CALL---//////////////////////////////

       /* if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);//REQUEST_CALL
            Log.w("CALL_PHONE PERMISSION ", "NEW CALL_PHONE PERMISSION IS GRANTED");

        }else{
            Log.w("CALL_PHONE PERMISSION ", "CALL_PHONE PERMISSION ALRADY GRANTED");
        }
*/


/*
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},REQUEST_CALL);//REQUEST_CALL
            Log.w("INTERNET PERMISSION ", "NEW INTERNET PERMISSION IS GRANTED");

        }else{
            Log.w("INTERNET PERMISSION ", "INTERNET PERMISSION ALRADY GRANTED");
        }*/
        ////////////////////////////---PERMISSION GRANTED FOR USER PHONE CALL---//////////////////////////////

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            session.logoutUser(getApplicationContext());
            mStoreUserInformation.clearInformation(getApplicationContext());
            mSignInInfromation.clearSignin(getApplicationContext());
            startActivityForResult(new Intent(MainActivity.this, SignInActivity.class),100);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////


    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView ivResult) {
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            profileImage.setImageBitmap(bitmap);
            profile_ProgressBar.setVisibility(View.GONE);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
}
